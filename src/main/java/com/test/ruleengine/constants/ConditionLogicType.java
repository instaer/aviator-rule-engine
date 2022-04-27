package com.test.ruleengine.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 条件逻辑运算类型
 */
@Getter
@AllArgsConstructor
public enum ConditionLogicType {

    /**
     * 当前条件单元满足则继续，否则跳过后续条件单元
     */
    AND(1, "&&", "所在条件单元必须满足，否则条件单元值为false"),

    /**
     * 当前条件单元满足则跳过后续条件单元，否则继续
     */
    OR(2, "||", "所在条件单元如果满足，则条件单元值为true");

    private final Integer code;
    private final String value;
    private final String desc;

    public static ConditionLogicType getConditionLogicType(Integer code) {
        return Arrays.stream(ConditionLogicType.values())
                .filter(e -> e.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public static Map<Integer, String> conditionLogicTypeMap = Arrays.stream(ConditionLogicType.values())
            .collect(Collectors.toMap(ConditionLogicType::getCode, ConditionLogicType::getDesc));
}
