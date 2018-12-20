package com.liferando;

import com.google.gson.Gson;
import com.liferando.domain.interfaces.ScoringInterface;
import com.liferando.domain.model.Score;
import com.liferando.domain.services.ConsumerService;
import com.liferando.domain.services.PlayService;
import com.liferando.domain.services.ScoreValidationService;
import lombok.extern.slf4j.Slf4j;

import static com.liferando.ServerPropReader.SERVER_PORT;
import static com.liferando.domain.interfaces.KafkaConnector.createConsumer;
import static spark.Spark.*;

@Slf4j
public class App {
    public static void main(String[] args) {

        ScoreValidationService scoreValidationService = new ScoreValidationService();
        PlayService playService = new PlayService(new ScoringInterface(new Gson()), scoreValidationService);
        ConsumerService consumerService = new ConsumerService(createConsumer(), new Gson(), playService, scoreValidationService);

        port(Integer.parseInt(ServerPropReader.getSystemOrDefault(SERVER_PORT).toString()));

        path("/health", () -> {
            get("/ping", (r, s) -> "pong");
        });

        path("/api/v1", () -> {
            before("/*", ((request, response) -> log.info("call received")));
            path("/play", () -> {
                get("/:count", (r, s) -> {
                    Integer count = Integer.parseInt(r.params("count"));
                    playService.play(new Score(count, System.currentTimeMillis()));
                    return "playing";
                });
                get("/status", (r,s) -> {
                    return "playing";
                });

            });

        });

        consumerService.play();
    }
}
