package com.liferando.domain.interfaces;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;

import static com.liferando.domain.interfaces.KafkaPropsReader.*;

public class KafkaConnector {


    public static Producer<String, String> createProducer() {

        Properties props = getProperties();
        Producer<String, String> producer = new KafkaProducer<>(props);
        return producer;
    }

    public static Consumer<String, String> createConsumer() {
        Properties properties = getConsumerProperties();
        return new KafkaConsumer<String, String>(properties);
    }

    private static Properties getConsumerProperties() {
        Properties props = getProperties();
        props.put(KEY_DESERIALIZER, getSystemOrDefault(KEY_DESERIALIZER));
        props.put(VALUE_DESERIALIZER, getSystemOrDefault(VALUE_DESERIALIZER));
        props.put(VALUE_DESERIALIZER, getSystemOrDefault(VALUE_DESERIALIZER));
        props.put(VALUE_DESERIALIZER, getSystemOrDefault(VALUE_DESERIALIZER));
        props.put(CLIENT_ID, getSystemOrDefault(CLIENT_ID));
        props.put(GROUP_ID,getSystemOrDefault(GROUP_ID));
        return props;
    }


    private static Properties getProperties() {
        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS, getSystemOrDefault(BOOTSTRAP_SERVERS));
        props.put(ACKS, getSystemOrDefault(ACKS));
        props.put(RETRIES, getSystemOrDefault(RETRIES));
        props.put(BATCH_SIZE, getSystemOrDefault(BATCH_SIZE));
        props.put(LINGER_MS, getSystemOrDefault(LINGER_MS));
        props.put(BUFFER_MEMORY, getSystemOrDefault(BUFFER_MEMORY));
        props.put(KEY_SERIALIZER, getSystemOrDefault(KEY_SERIALIZER));
        props.put(VALUE_SERIALIZER, getSystemOrDefault(VALUE_SERIALIZER));

        return props;
    }
}
