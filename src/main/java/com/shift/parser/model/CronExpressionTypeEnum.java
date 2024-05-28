package com.shift.parser.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CronExpressionTypeEnum {
    MINUTES(0, "minutes", 0, 59),
    HOUR(1, "hour", 0, 23),
    DAY_OF_MONTH(2, "day of month", 1, 31),
    MONTH(3, "month", 1, 12),
    DAY_OF_WEEK(4, "day of week", 1, 7),
    COMMAND(5, "command", null, null);

    private final Integer position;
    private final String name;
    private final Integer minValue;
    private final Integer maxValue;
}
