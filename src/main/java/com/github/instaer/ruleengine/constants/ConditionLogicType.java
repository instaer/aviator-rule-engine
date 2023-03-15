package com.github.instaer.ruleengine.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * condition logical operation type
 */
@Getter
@AllArgsConstructor
public enum ConditionLogicType {

    /**
     * if current condition unit evaluates to true, continue to execute backwards,
     * otherwise skip the following condition unit and return false directly
     */
    AND(1, "&&"),

    /**
     * If current condition unit evaluates to true, skip the following condition unit and return true directly,
     * otherwise continue to execute backward.
     */
    OR(2, "||");

    private final Integer code;
    private final String value;

    public static ConditionLogicType getConditionLogicType(Integer code) {
        return Arrays.stream(ConditionLogicType.values())
                .filter(e -> e.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public static Map<Integer, String> conditionLogicTypeMap = Arrays.stream(ConditionLogicType.values())
            .collect(Collectors.toMap(ConditionLogicType::getCode, ConditionLogicType::getValue));
}
