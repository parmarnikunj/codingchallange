package com.liferando.domain.services;

import com.google.gson.Gson;
import com.liferando.domain.model.Score;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.Arrays;

import static com.liferando.domain.interfaces.KafkaPropsReader.CONSUMER_TOPIC;
import static com.liferando.domain.interfaces.KafkaPropsReader.getSystemOrDefault;

@AllArgsConstructor
@Slf4j
public class ConsumerService {

    private Consumer consumer;
    private Gson gson;
    private PlayService playService;
    private ScoreValidationService scoreValidationService;

    public void play() {

        consumer.subscribe(Arrays.asList(getSystemOrDefault(CONSUMER_TOPIC).toString()));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            records.forEach(record -> {
                Score score = gson.fromJson(record.value(), Score.class);
                if (scoreValidationService.isWinner(score)) {
                    log.info("You are the Winner!");
                } else {
                    if (scoreValidationService.valid(score)) {
                        playService.play(playService.applyRule(score.getValue()));
                    }
                    log.info("not valid score!", score);
                }
            });
        }
    }
}
