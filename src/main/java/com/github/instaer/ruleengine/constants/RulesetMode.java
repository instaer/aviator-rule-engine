package com.github.instaer.ruleengine.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RulesetMode {
    BUILDING(1),

    BUILT(2);

    private final Integer code;
}