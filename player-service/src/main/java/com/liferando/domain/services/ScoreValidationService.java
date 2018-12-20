package com.liferando.domain.services;

import com.liferando.domain.model.ScoreChangedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreValidationService {
    public boolean valid(ScoreChangedEvent scoreChangedEvent) {
        return scoreChangedEvent.getValue() != 0;
    }

    public boolean isWinner(ScoreChangedEvent scoreChangedEvent) {
        return scoreChangedEvent.getValue() == 1;
    }
}
