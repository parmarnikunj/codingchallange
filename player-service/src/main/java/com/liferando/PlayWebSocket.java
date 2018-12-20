package com.liferando;

import com.google.gson.Gson;
import com.liferando.domain.interfaces.ScoringInterface;
import com.liferando.domain.model.Score;
import com.liferando.domain.services.ConsumerService;
import com.liferando.domain.services.PlayService;
import com.liferando.domain.services.ScoreValidationService;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import static com.liferando.domain.interfaces.KafkaConnector.createConsumer;


@WebSocket
public class PlayWebSocket {

    // Store sessions if you want to, for example, broadcast a message to all users
    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();
    private ScoreValidationService scoreValidationService;
    PlayService playService;
    ConsumerService consumerService;


    @OnWebSocketConnect
    public void connected(Session session) throws IOException {
        sessions.add(session);
        scoreValidationService = new ScoreValidationService();
        playService = new PlayService(new ScoringInterface(new Gson()), scoreValidationService);
        consumerService = new ConsumerService(createConsumer(), new Gson(), playService, scoreValidationService);
        consumerService.start(session);
        session.getRemote().sendString("connected");
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        sessions.remove(session);
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        Integer count = Integer.parseInt(message);
        session.getRemote().sendString("Game Started with Count : " + count); // and send it back
        playService.play(new Score(count, System.currentTimeMillis()));
    }

}