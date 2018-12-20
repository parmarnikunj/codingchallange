package com.liferando.domain.services;

import com.liferando.domain.interfaces.ScoringInterface;
import com.liferando.domain.model.ScoreChangeCommand;
import com.liferando.domain.model.ScoreChangedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.liferando.domain.model.EventType.SCORE_CHANGED;


@Slf4j
@AllArgsConstructor
public class PlayService {

    private ScoringInterface scoringInterface;
    private ScoreValidationService scoreValidationService;

    public PlayService(ScoringInterface scoringInterface) {

        this.scoringInterface = scoringInterface;
    }

    public void play(ScoreChangeCommand scoreChangeCommand) {
        if (scoreValidationService.valid(scoreChangeCommand)) {
            scoringInterface.send(new ScoreChangedEvent(scoreChangeCommand.getScore(), SCORE_CHANGED, System.currentTimeMillis()));
        }
        log.info("not sending invalid scoreChangeCommand!");
    }

    public ScoreChangeCommand applyRule(int oldScore) {

        if (oldScore == 1) {
            log.info("Winner !!!");
        }
        return new ScoreChangeCommand(businessLogic(oldScore));
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
