package com.github.instaer.ruleengine.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Rule logic operator type
 */
@Getter
@AllArgsConstructor
public enum RuleLogicType {

    /**
     * <b>XOR</b>
     * <p>Among the two rules with adjacent priorities,
     * if the higher priority rule matches, return the corresponding return value directly
     * without verifying the subsequent rules.</p>
     * <p>Otherwise, verify if the lower priority rule is satisfied.
     * If it is satisfied, return the corresponding return value.
     * If not, return null or the default return value (if set) set by the rule set.</p>
     */
    XOR("XOR", ""),

    /**
     * <b>AND</b>
     * <p>Among the two rules with adjacent priorities, only when both the higher and
     * lower priority rules match at the same time, the corresponding return value will be returned.</p>
     * <p>Otherwise, return null or the default return value (if set) set by the rule set.</p>
     */
    AND("AND", "&&");

    private final String desc;
    private final String value;

    public static RuleLogicType getEnum(String name) {
        return Arrays.stream(values()).filter(e -> e.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static Map<String, String> getOptions() {
        return Arrays.stream(values())
                .collect(Collectors.toMap(Enum::name, e -> e.desc, (v1, v2) -> v2, LinkedHashMap::new));
    }
}
