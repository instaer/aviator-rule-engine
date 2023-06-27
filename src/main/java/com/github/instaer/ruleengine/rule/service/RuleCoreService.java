package com.github.instaer.ruleengine.rule.service;

import com.github.instaer.ruleengine.aviator.ExpressionExecuteService;
import com.github.instaer.ruleengine.constants.RulesetMode;
import com.github.instaer.ruleengine.exception.RuleRunTimeException;
import com.github.instaer.ruleengine.rule.entity.RulesetInfoEntity;
import com.github.instaer.ruleengine.rule.repository.RulesetInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class RuleCoreService {

    @Autowired
    private RulesetInfoRepository rulesetInfoRepository;

    @Autowired
    private ExpressionExecuteService expressionExecuteService;

    public Map<String, Object> executeRuleset(String rulesetCode, Map<String, Object> paraMap) {
        if (StringUtils.isEmpty(rulesetCode)) {
            throw new RuleRunTimeException("invalid parameter(rulesetCode)");
        }

        if (CollectionUtils.isEmpty(paraMap)) {
            throw new RuleRunTimeException("invalid parameter(paraMap)");
        }

        RulesetInfoEntity rulesetInfoEntity = Optional.ofNullable(rulesetInfoRepository.findByCode(rulesetCode))
                .orElseThrow(() -> new RuleRunTimeException("invalid parameter(rulesetCode)"));

        if (!RulesetMode.BUILT.getCode().equals(rulesetInfoEntity.getMode())) {
            throw new RuleRunTimeException("expression under the ruleset(" + rulesetCode + ")  is not built");
        }

        String expression = rulesetInfoEntity.getExpression();
        if (StringUtils.isBlank(expression)) {
            throw new RuleRunTimeException("expression under the ruleset(" + rulesetCode + ")  is invalid");
        }

        log.info("## execute rule set:{}", rulesetCode);
        return expressionExecuteService.execute(expression, paraMap);
    }
}