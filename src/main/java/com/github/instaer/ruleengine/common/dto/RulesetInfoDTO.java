package com.github.instaer.ruleengine.common.dto;

import com.github.instaer.ruleengine.rule.entity.RulesetInfoEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RulesetInfoDTO extends RulesetInfoEntity {
    private Integer page;
    private Integer size;

    private Map<String, Object> paraMap;
}