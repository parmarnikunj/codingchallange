package com.liferando.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ScoreChangeCommand {
    private int score;
}
