package com.test.ruleengine.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RuleMode {

    RULE_SET(1),

    EXPRESSION_SET(2);

    private final Integer code;
}