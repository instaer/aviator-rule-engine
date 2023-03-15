package com.github.instaer.ruleengine.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Condition relational operation type
 */
@Getter
@AllArgsConstructor
public enum ConditionRelationType {

    EQUAL(1, "decimal(%s) == decimal(%s)"),

    NOT_EQUAL(2, "decimal(%s) != decimal(%s)"),

    LESS_EQUAL(3, "decimal(%s) <= decimal(%s)"),

    GREATER_EQUAL(4, "decimal(%s) >= decimal(%s)"),

    LESS(5, "decimal(%s) < decimal(%s)"),

    GREATER(6, "decimal(%s) > decimal(%s)"),

    /**
     * a string type element is included in a list
     */
    INCLUDE_IN_LIST(7, "include(seq.set(%s), str(%s))"),

    /**
     * a string type element is not included in a list
     */
    NOT_INCLUDE_IN_LIST(8, "!include(seq.set(%s), str(%s))"),

    /**
     * some characters in a string are contained in a list
     */
    SOME_CONTAINS_IN_LIST(9, "nil != seq.some(seq.set(%s), lambda(x) -> string.indexOf(str(%s), x) > -1 end)"),

    /**
     * none characters of a string are contained in a list
     */
    NONE_CONTAINS_IN_LIST(10, "seq.not_any(seq.set(%s), lambda(x) -> string.indexOf(str(%s), x) > -1 end)"),

    /**
     * match regular expression
     */
    REGULAR(11, "str(%s) =~ %s");

    private final Integer code;
    private final String format;

    public static ConditionRelationType getConditionRelationType(Integer code) {
        return Arrays.stream(ConditionRelationType.values())
                .filter(e -> e.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public static Map<Integer, String> conditionRelationTypeMap = Arrays.stream(ConditionRelationType.values())
            .collect(Collectors.toMap(ConditionRelationType::getCode, ConditionRelationType::name));
}
