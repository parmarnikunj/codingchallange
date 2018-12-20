package com.liferando.domain.services;

import com.google.gson.Gson;
import com.liferando.domain.model.Score;
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
                    Score score = gson.fromJson(record.value(), Score.class);
                    if (scoreValidationService.isWinner(score)) {
                        log.info("You are the Winner!");
                        completableFuture.complete("Done");
                        try {
                            session.getRemote().sendString("You are the winner!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (scoreValidationService.valid(score)) {
                            playService.play(playService.applyRule(score.getValue()));
                        }
                        log.info("not valid score!", score);
                    }
                });
            }
        });

        return completableFuture;

    }
}
