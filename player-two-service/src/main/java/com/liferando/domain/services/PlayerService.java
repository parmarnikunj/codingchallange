package com.liferando.domain.services;

import com.liferando.domain.interfaces.KafkaConnector;
import org.apache.kafka.clients.producer.ProducerRecord;

public class PlayerService {

    public static void play(Integer count) {
        System.out.println("playing with count " + count);

        KafkaConnector.createProducer()
                .send(new ProducerRecord<String, String>("player-1-topic", Integer.toString(count), Integer.toString(count)));
    }
}
