package com.github.instaer.ruleengine.condition;

import com.github.instaer.ruleengine.constants.ConditionRelationType;
import com.github.instaer.ruleengine.exception.RuleRunTimeException;
import org.springframework.stereotype.Component;

@Component
public class RegexCondition implements Condition {

    @Override
    public ConditionRelationType[] relationTypes() {
        return new ConditionRelationType[]{ConditionRelationType.REGEX};
    }

    @Override
    public String build(ConditionInstance conditionInstance) {
        try {
            return String.format(format(conditionInstance), conditionInstance.getVariableName(), conditionInstance.getReferenceValue());
        } catch (Exception e) {
            throw new RuleRunTimeException(e);
        }
    }
}