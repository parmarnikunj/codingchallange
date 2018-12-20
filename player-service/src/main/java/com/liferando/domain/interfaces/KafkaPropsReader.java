package com.liferando.domain.interfaces;

import java.util.HashMap;
import java.util.Map;

public class KafkaPropsReader {

    public static final String BOOTSTRAP_SERVERS = "bootstrap.servers";
    public static final String ACKS = "acks";
    public static final String RETRIES = "retries";
    public static final String BATCH_SIZE = "batch.size";
    public static final String LINGER_MS = "linger.ms";
    public static final String BUFFER_MEMORY = "buffer.memory";
    public static final String KEY_SERIALIZER = "key.serializer";
    public static final String KEY_DESERIALIZER = "key.deserializer";
    public static final String VALUE_SERIALIZER = "value.serializer";
    public static final String VALUE_DESERIALIZER = "value.deserializer";
    public static final String TOPIC_NAME = "topic.name";
    public static final String CONSUMER_TOPIC = "consumer.topic";
    public static final String CLIENT_ID = "client.id";
    public static final String GROUP_ID = "group.id";

    private static Map<String, Object> defaultProps() {
        HashMap<String, Object> defaultProps = new HashMap<>();
        defaultProps.put(BOOTSTRAP_SERVERS, "localhost:9091");
        defaultProps.put(ACKS, "all");
        defaultProps.put(RETRIES, 0);
        defaultProps.put(BATCH_SIZE, 16384);
        defaultProps.put(LINGER_MS, 1);
        defaultProps.put(BUFFER_MEMORY, 33554432);
        defaultProps.put(KEY_SERIALIZER, "org.apache.kafka.common.serialization.StringSerializer");
        defaultProps.put(KEY_DESERIALIZER, "org.apache.kafka.common.serialization.StringDeserializer");
        defaultProps.put(VALUE_SERIALIZER, "org.apache.kafka.common.serialization.StringSerializer");
        defaultProps.put(VALUE_DESERIALIZER, "org.apache.kafka.common.serialization.StringDeserializer");
        defaultProps.put(TOPIC_NAME, "default_topic");
        defaultProps.put(CONSUMER_TOPIC, "default_topic");
        defaultProps.put(CLIENT_ID, "default_client");
        defaultProps.put(GROUP_ID, "default_group");

        return defaultProps;
    }

    public static Object getSystemOrDefault(String prop) {
        String systemPropVal = System.getenv(toSystemPropName(prop));
        return systemPropVal == null ? defaultProps().get(prop) : systemPropVal;
    }

    public static String toSystemPropName(String prop) {
        return prop.toUpperCase().replace(".","_");
    }
}
