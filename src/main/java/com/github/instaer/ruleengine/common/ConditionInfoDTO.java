package com.github.instaer.ruleengine.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConditionInfoDTO {
    private Long ruleId;
    private Integer page;
    private Integer size;
}