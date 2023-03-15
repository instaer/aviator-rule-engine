package com.github.instaer.ruleengine.common;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RulesetInfoDTO {
    private Integer page;
    private Integer size;

    private String rulesetCode;
    private Map<String, Object> paraMap;
}