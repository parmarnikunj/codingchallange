package com.liferando;

import java.util.HashMap;
import java.util.Map;

public class ServerPropReader {

    public static final String SERVER_PORT = "server.port";

    private static Map<String, Object> defaultProps() {
        HashMap<String, Object> defaultProps = new HashMap<>();
        defaultProps.put(SERVER_PORT, 4000);
        return defaultProps;
    }

    public static Object getSystemOrDefault(String prop) {
        String envProp = System.getenv(prop);
        return envProp == null ? defaultProps().get(prop) : envProp;
    }

}
