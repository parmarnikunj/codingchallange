package com.liferando.domain.services;

import com.liferando.domain.interfaces.ScoringInterface;
import com.liferando.domain.model.ScoreChangedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@AllArgsConstructor
public class PlayService {

    private ScoringInterface scoringInterface;
    private ScoreValidationService scoreValidationService;

    public PlayService(ScoringInterface scoringInterface) {

        this.scoringInterface = scoringInterface;
    }

    public void play(ScoreChangedEvent scoreChangedEvent) {
        if (scoreValidationService.valid(scoreChangedEvent)) {
            scoringInterface.send(scoreChangedEvent);
        }
        log.info("not sending invalid scoreChangedEvent!");
    }

    public ScoreChangedEvent applyRule(int oldScore) {

        if (oldScore == 1) {
            log.info("Winner !!!");
        }
        return new ScoreChangedEvent(businessLogic(oldScore), System.currentTimeMillis());
    }


    private int businessLogic(int score) {
        if ((score - 1) % 3 == 0) {
            return (score - 1) / 3;
        }

        if (score % 3 == 0) {
            return score / 3;
        }
        if ((score + 1) % 3 == 0) {
            return (score + 1) / 3;
        }
        return 0;
    }
}
