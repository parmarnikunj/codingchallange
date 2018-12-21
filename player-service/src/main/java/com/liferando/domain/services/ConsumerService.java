package com.liferando.domain.services;

import com.google.gson.Gson;
import com.liferando.domain.model.ScoreChangeCommand;
import com.liferando.domain.model.ScoreChangedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.liferando.domain.interfaces.KafkaPropsReader.CONSUMER_TOPIC;
import static com.liferando.domain.interfaces.KafkaPropsReader.getSystemOrDefault;

@AllArgsConstructor
@Slf4j
public class ConsumerService {

    private Consumer consumer;
    private Gson gson;
    private PlayService playService;
    private ScoreValidationService scoreValidationService;

    public Future<String> start(Session session) {

        consumer.subscribe(Arrays.asList(getSystemOrDefault(CONSUMER_TOPIC).toString()));

        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(record -> {
                    ScoreChangedEvent scoreChangedEvent = gson.fromJson(record.value(), ScoreChangedEvent.class);
                    if (scoreValidationService.isWinner(scoreChangedEvent)) {
                        log.info("You are the Winner!");
                        completableFuture.complete("Done");
                        reportWinning(session);
                    } else {
                        ScoreChangeCommand scoreChangeCommand = new ScoreChangeCommand(scoreChangedEvent.getValue());
                        if (scoreValidationService.valid(scoreChangeCommand)) {
                            reportDomainEvent(session, scoreChangedEvent);
                            playService.play(playService.applyRule(scoreChangedEvent.getValue()));
                        }
                        log.info("not valid scoreChangedEvent!", scoreChangedEvent);
                    }
                });
            }
        });

        return completableFuture;

    }

    private void reportDomainEvent(Session session, ScoreChangedEvent scoreChangedEvent) {
        try {
            session.getRemote().sendString("your current score is: "+ scoreChangedEvent.getValue());
        } catch (IOException e) {
            log.error("error: ", e);
        }
    }

    public void reportWinning(Session session) {
        try {
            session.getRemote().sendString("You are the winner!");
        } catch (IOException e) {
            log.error("error: ", e);
        }
    }

    public void close() {
        consumer.close();
    }
}
