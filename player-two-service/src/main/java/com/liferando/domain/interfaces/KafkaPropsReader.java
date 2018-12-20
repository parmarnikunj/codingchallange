package com.liferando.domain.interfaces;

import java.util.HashMap;
import java.util.Map;

public class KafkaProperties {
    public static final String BOOTSTRAP_SERVERS = "bootstrap.servers";
    public static final String ACKS = "acks";
    public static final String RETRIES = "retries";
    public static final String BATCH_SIZE = "batch.size";
    public static final String LINGER_MS = "linger.ms";
    public static final String BUFFER_MEMORY = "buffer.memory";
    public static final String KEY_SERIALIZER = "key.serializer";
    public static final String VALUE_SERIALIZER = "value.serializer";

    private static Map<String, Object> defaultProps() {
        HashMap<String, Object> defaultProps = new HashMap<>();
        defaultProps.put(BOOTSTRAP_SERVERS, "localhost:9091");
        defaultProps.put(ACKS, "all");
        defaultProps.put(RETRIES, 0);
        defaultProps.put(BATCH_SIZE, 16384);
        defaultProps.put(LINGER_MS, 1);
        defaultProps.put(BUFFER_MEMORY, 33554432);
        defaultProps.put(KEY_SERIALIZER, "org.apache.kafka.common.serialization.StringSerializer");
        defaultProps.put(VALUE_SERIALIZER, "org.apache.kafka.common.serialization.StringSerializer");

        return defaultProps;
    }

    public static Object getSystemOrDefault(String prop) {
        String systemProp = System.getenv().get(prop);
        return systemProp == null ? defaultProps().get(prop) : systemProp;
    }
}
