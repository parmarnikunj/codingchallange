package com.liferando.domain.interfaces;

import com.google.gson.Gson;
import com.liferando.domain.model.ScoreChangedEvent;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.concurrent.CompletableFuture;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(KafkaConnector.class)
public class ScoringInterfaceTest {

    private ScoringInterface scoringInterface = new ScoringInterface(new Gson());

    @Mock
    private Producer producer;

    @Test
    public void test_send_score() {

        mockStatic(KafkaConnector.class);
        given(KafkaConnector.createProducer()).willReturn(producer);
        given(producer.send(any(ProducerRecord.class))).willReturn(new CompletableFuture());

        scoringInterface.send(new ScoreChangedEvent(12, System.currentTimeMillis()));

    }
}