package com.github.instaer.ruleengine.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RuleInfoDTO {
    private Long rulesetId;
    private Integer page;
    private Integer size;
}