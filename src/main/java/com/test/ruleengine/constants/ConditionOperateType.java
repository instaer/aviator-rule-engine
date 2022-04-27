package com.test.ruleengine.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 条件关系运算类型
 */
@Getter
@AllArgsConstructor
public enum ConditionOperateType {

    EQUAL(1, "等于", "decimal(%s) == decimal(%s)"),

    NOT_EQUAL(2, "不等于", "decimal(%s) != decimal(%s)"),

    LESS_EQUAL(3, "小于等于", "decimal(%s) <= decimal(%s)"),

    GREATER_EQUAL(4, "大于等于", "decimal(%s) >= decimal(%s)"),

    LESS(5, "小于", "decimal(%s) < decimal(%s)"),

    GREATER(6, "大于", "decimal(%s) > decimal(%s)"),

    /**
     * 判断字符串类型元素是否包含于某个列表中
     */
    INCLUDE_IN_LIST(7, "包括", "include(seq.set(%s), str(%s))"),

    /**
     * 判断字符串类型元素是否不包含在某个列表中
     */
    NOT_INCLUDE_IN_LIST(8, "不包括", "!include(seq.set(%s), str(%s))"),

    /**
     * 判断字符串部分字符是否包含在某个列表中
     */
    SOME_CONTAINS_IN_LIST(9, "包含", "nil != seq.some(seq.set(%s), lambda(x) -> string.indexOf(str(%s), x) > -1 end)"),

    /**
     * 判断字符串所有字符是否都不包含在某个列表中
     */
    NONE_CONTAINS_IN_LIST(10, "不包含", "seq.not_any(seq.set(%s), lambda(x) -> string.indexOf(str(%s), x) > -1 end)"),

    /**
     * 正则表达式判断
     */
    REGULAR(11, "正则", "str(%s) =~ %s");

    private final Integer code;
    private final String desc;
    private final String format;

    public static ConditionOperateType getConditionOperateType(Integer code) {
        return Arrays.stream(ConditionOperateType.values())
                .filter(e -> e.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public static Map<Integer, String> conditionOperateTypeMap = Arrays.stream(ConditionOperateType.values())
            .collect(Collectors.toMap(ConditionOperateType::getCode, ConditionOperateType::getDesc));
}
