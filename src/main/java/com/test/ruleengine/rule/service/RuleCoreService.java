package com.test.ruleengine.rule.service;

import com.test.ruleengine.aviator.ExpressionExecuteService;
import com.test.ruleengine.constants.EnableStatus;
import com.test.ruleengine.constants.RuleMode;
import com.test.ruleengine.exception.RuleRunTimeException;
import com.test.ruleengine.rule.entity.RuleInfoEntity;
import com.test.ruleengine.rule.repository.RuleInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class RuleCoreService {

    @Autowired
    private RuleInfoRepository ruleInfoRepository;

    @Autowired
    private ExpressionExecuteService expressionExecuteService;

    public boolean executeRule(String ruleCode, Map<String, Object> paramMap) {
        if (StringUtils.isEmpty(ruleCode)) {
            throw new RuleRunTimeException("invalid ruleCode");
        }

        RuleInfoEntity ruleInfoEntity = Optional.ofNullable(ruleInfoRepository.findByCode(ruleCode))
                .orElseThrow(() -> new RuleRunTimeException("invalid ruleCode"));

        if (!EnableStatus.ENABLED.getCode().equals(ruleInfoEntity.getStatus())) {
            throw new RuleRunTimeException("rule(" + ruleCode + ") is not enabled");
        }

        if (!RuleMode.EXPRESSION_SET.getCode().equals(ruleInfoEntity.getMode())) {
            throw new RuleRunTimeException("expression under the rule(" + ruleCode + ")  is not generated");
        }

        String ruleExpression = ruleInfoEntity.getExpression();
        if (StringUtils.isBlank(ruleExpression)) {
            throw new RuleRunTimeException("expression under the rule(" + ruleCode + ")  is invalid");
        }

        return expressionExecuteService.execute(ruleExpression, paramMap);
    }
}