package com.liferando;

import com.liferando.domain.services.PlayerService;
import lombok.extern.slf4j.Slf4j;

import static spark.Spark.*;

@Slf4j
public class App {
    public static void main(String[] args) {
        port(4000);
        path("/api/v1", () -> {
            before("/*", ((request, response) -> log.info("call received")));
            path("/play", () -> {
                get("/:count", (r, s) -> {
                    Integer count = Integer.parseInt(r.params("count"));
                    PlayerService.play(count);
                    return "done";
                });

            });

        });
    }
}
