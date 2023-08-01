package com.github.instaer.ruleengine.common.dto;

import com.github.instaer.ruleengine.common.entity.RuleInfoEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RuleInfoDTO extends RuleInfoEntity {
    private Integer page;
    private Integer size;
}