package com.shift.parser.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CronOperatorType {
    ALL("*"),
    BETWEEN("-"),
    COMA(",");

    private final String operator;
}
