package com.liferando.domain.services;

import com.liferando.domain.interfaces.ScoringInterface;
import com.liferando.domain.model.Score;
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

        Score score = playService.applyRule(56);

        assertThat(score).isNotNull();
        assertThat(score.getValue()).isEqualTo(19);

        score.setValue(19);
        assertThat(playService.applyRule(score.getValue()).getValue()).isEqualTo(6);

        score.setValue(6);
        assertThat(playService.applyRule(score.getValue()).getValue()).isEqualTo(2);

        score.setValue(2);
        assertThat(playService.applyRule(score.getValue()).getValue()).isEqualTo(1);

    }

}