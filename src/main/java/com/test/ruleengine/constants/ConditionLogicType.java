package com.test.ruleengine.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 逻辑运算类型
 */
@Getter
@AllArgsConstructor
public enum ConditionLogicType {

    /**
     * 当前条件满足则继续，否则跳过后续条件
     */
    AND(1, "&&", "当前条件必须满足，否则规则不通过"),

    /**
     * 当前条件满足则跳过后续条件，否则继续
     */
    OR(2, "||", "当前条件如果满足，则规则通过");

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