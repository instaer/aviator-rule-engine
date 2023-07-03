package com.github.instaer.ruleengine.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Condition relation operator type
 */
@Getter
@AllArgsConstructor
public enum ConditionRelationType {

    EQUAL("等于", "%s == %s"),

    NOT_EQUAL("不等于", "%s != %s"),

    LESS_EQUAL("小于等于", "%s <= %s"),

    GREATER_EQUAL("大于等于", "%s >= %s"),

    LESS("小于", "%s < %s"),

    GREATER("大于", "%s > %s"),

    /**
     * a string type element is included in a list
     */
    INCLUDE_IN_LIST("列表包含指定字符串", "include(seq.set(%s), str(%s))"),

    /**
     * a string type element is not included in a list
     */
    NOT_INCLUDE_IN_LIST("列表不包含指定字符串", "!include(seq.set(%s), str(%s))"),

    /**
     * some characters in a string are contained in a list
     */
    SOME_CONTAINS_IN_LIST("列表包含指定字符串的某一部分", "nil != seq.some(seq.set(%s), lambda(x) -> string.indexOf(str(%s), x) > -1 end)"),

    /**
     * none characters of a string are contained in a list
     */
    NONE_CONTAINS_IN_LIST("列表不包含指定字符串的任何部分", "seq.not_any(seq.set(%s), lambda(x) -> string.indexOf(str(%s), x) > -1 end)"),

    /**
     * contain specified characters
     */
    STRING_CONTAINS("包含指定字符", "string.contains(str(%s),str(%s))"),

    /**
     * not contain specified characters
     */
    STRING_NOT_CONTAINS("不包含指定字符", "!string.contains(str(%s),str(%s))"),

    /**
     * start with specified character
     */
    STRING_STARTSWITH("以指定字符开始", "string.startsWith(str(%s),str(%s))"),

    /**
     * not start with specified character
     */
    STRING_NOT_STARTSWITH("不以指定字符开始", "!string.startsWith(str(%s),str(%s))"),

    /**
     * ends with specified character
     */
    STRING_ENDSWITH("以指定字符结束", "!string.endsWith(str(%s),str(%s))"),

    /**
     * not end with specified character
     */
    STRING_NOT_ENDSWITH("不以指定字符结束", "!string.endsWith(str(%s),str(%s))"),

    /**
     * match regular expression
     */
    REGEX("正则", "str(%s) =~ %s");

    private final String desc;
    private final String format;

    public static ConditionRelationType getConditionRelationType(String name) {
        return Arrays.stream(ConditionRelationType.values())
                .filter(t -> t.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public static Map<String, String> conditionRelationTypeMap = Arrays.stream(ConditionRelationType.values())
            .collect(Collectors.toMap(ConditionRelationType::name, ConditionRelationType::getDesc));
}
