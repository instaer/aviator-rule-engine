package com.github.instaer.ruleengine.rule.service;

import com.github.instaer.ruleengine.aviator.ExpressionExecuteService;
import com.github.instaer.ruleengine.constants.RulesetMode;
import com.github.instaer.ruleengine.exception.RuleRunTimeException;
import com.github.instaer.ruleengine.rule.entity.RulesetInfoEntity;
import com.github.instaer.ruleengine.rule.repository.RulesetInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class RuleCoreService {

    @Autowired
    private RulesetInfoRepository rulesetInfoRepository;

    @Autowired
    private ExpressionExecuteService expressionExecuteService;

    public boolean executeRuleset(String rulesetCode, Map<String, Object> paraMap) {
        if (StringUtils.isEmpty(rulesetCode)) {
            throw new RuleRunTimeException("invalid rulesetCode");
        }

        RulesetInfoEntity rulesetInfoEntity = Optional.ofNullable(rulesetInfoRepository.findByCode(rulesetCode))
                .orElseThrow(() -> new RuleRunTimeException("invalid rulesetCode"));

        if (!RulesetMode.BUILDING.getCode().equals(rulesetInfoEntity.getMode())) {
            throw new RuleRunTimeException("expression under the ruleset(" + rulesetCode + ")  is not built");
        }

        String expression = rulesetInfoEntity.getExpression();
        if (StringUtils.isBlank(expression)) {
            throw new RuleRunTimeException("expression under the ruleset(" + rulesetCode + ")  is invalid");
        }

        return expressionExecuteService.execute(expression, paraMap);
    }
}