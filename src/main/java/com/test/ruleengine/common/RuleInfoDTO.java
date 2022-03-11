package com.test.ruleengine.common;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RuleInfoDTO {
    private Integer page;
    private Integer size;

    private String ruleCode;
    private Map<String, Object> paramMap;
}