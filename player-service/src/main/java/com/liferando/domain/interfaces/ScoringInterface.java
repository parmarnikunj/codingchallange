package com.liferando.domain.interfaces;

import com.google.gson.Gson;
import com.liferando.domain.model.ScoreChangedEvent;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;

import static com.liferando.domain.interfaces.KafkaPropsReader.TOPIC_NAME;
import static com.liferando.domain.interfaces.KafkaPropsReader.getSystemOrDefault;

@AllArgsConstructor
public class ScoringInterface {

    private Gson gson;

    public void send(ScoreChangedEvent scoreChangedEvent) {
        KafkaConnector.createProducer()
                .send(new ProducerRecord<String, String>(
                        getSystemOrDefault(TOPIC_NAME).toString(),
                        gson.toJson(scoreChangedEvent))
                );
    }
}
