package com.liferando.domain.services;

import com.liferando.domain.interfaces.ScoringInterface;
import com.liferando.domain.model.ScoreChangeCommand;
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

        ScoreChangeCommand scoreChangeCommand = playService.applyRule(56);

        assertThat(scoreChangeCommand).isNotNull();
        assertThat(scoreChangeCommand.getScore()).isEqualTo(19);

        scoreChangeCommand.setScore(19);
        assertThat(playService.applyRule(scoreChangeCommand.getScore()).getScore()).isEqualTo(6);

        scoreChangeCommand.setScore(6);
        assertThat(playService.applyRule(scoreChangeCommand.getScore()).getScore()).isEqualTo(2);

        scoreChangeCommand.setScore(2);
        assertThat(playService.applyRule(scoreChangeCommand.getScore()).getScore()).isEqualTo(1);

    }

}