package com.test.ruleengine.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnableStatus {

    ENABLED(1),
    DISABLED(0);

    private final Integer code;
}