package com.liferando.domain.services;

import com.liferando.domain.interfaces.ScoringInterface;
import com.liferando.domain.model.ScoreChangedEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PlayServiceTest {

    @Mock
    private ScoringInterface scoringInterface;

    private PlayService playService = new PlayService(scoringInterface);

    @Test
    public void check_rules_works_as_expected() {

        ScoreChangedEvent scoreChangedEvent = playService.applyRule(56);

        assertThat(scoreChangedEvent).isNotNull();
        assertThat(scoreChangedEvent.getValue()).isEqualTo(19);

        scoreChangedEvent.setValue(19);
        assertThat(playService.applyRule(scoreChangedEvent.getValue()).getValue()).isEqualTo(6);

        scoreChangedEvent.setValue(6);
        assertThat(playService.applyRule(scoreChangedEvent.getValue()).getValue()).isEqualTo(2);

        scoreChangedEvent.setValue(2);
        assertThat(playService.applyRule(scoreChangedEvent.getValue()).getValue()).isEqualTo(1);

    }

}