package com.liferando.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreChangedEvent {
    private int value;
    private EventType eventType;
    private long time;
}
