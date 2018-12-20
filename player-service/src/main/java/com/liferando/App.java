package com.liferando;

import lombok.extern.slf4j.Slf4j;

import static com.liferando.ServerPropReader.SERVER_PORT;
import static spark.Spark.*;

@Slf4j
public class App {
    public static void main(String[] args) {

        port(Integer.parseInt(ServerPropReader.getSystemOrDefault(SERVER_PORT).toString()));

        webSocket("/start", PlayWebSocket.class);
        init();

        get("/health/ping", (r,s) -> "pong");
    }
}
