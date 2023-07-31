package com.github.instaer.ruleengine.common.dto;

import com.github.instaer.ruleengine.rule.entity.ConditionInfoEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConditionInfoDTO extends ConditionInfoEntity {
    private Integer page;
    private Integer size;
}