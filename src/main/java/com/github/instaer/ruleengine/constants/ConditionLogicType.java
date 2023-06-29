package com.github.instaer.ruleengine.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Condition logic operator type
 */
@Getter
@AllArgsConstructor
public enum ConditionLogicType {

    /**
     * if current condition evaluates to true, continue to execute backwards,
     * otherwise skip the following condition and return false directly
     */
    AND("AND", "&&"),

    /**
     * If current condition evaluates to true, skip the following condition and return true directly,
     * otherwise continue to execute backward.
     */
    OR("OR", "||");

    private final String desc;
    private final String value;

    public static ConditionLogicType getConditionLogicType(String name) {
        return Arrays.stream(ConditionLogicType.values())
                .filter(t -> t.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public static Map<String, String> conditionLogicTypeMap = Arrays.stream(ConditionLogicType.values())
            .collect(Collectors.toMap(ConditionLogicType::name, ConditionLogicType::getDesc));
}
