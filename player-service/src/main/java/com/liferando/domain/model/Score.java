package com.liferando.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Score {
    private int value;
    private long time;
}
