package com.liferando.domain.services;

import com.liferando.domain.model.Score;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreValidationService {
    public boolean valid(Score score) {
        return score.getValue() != 0;
    }

    public boolean isWinner(Score score) {
        return score.getValue() == 1;
    }
}
