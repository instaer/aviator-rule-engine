package com.github.instaer.ruleengine.condition;

import com.github.instaer.ruleengine.constants.ConditionRelationType;
import com.github.instaer.ruleengine.exception.RuleRunTimeException;
import org.springframework.stereotype.Component;

import static com.github.instaer.ruleengine.constants.ConditionRelationType.*;

@Component
public class RegexCondition implements Condition {

    @Override
    public ConditionRelationType[] relationTypes() {
        return new ConditionRelationType[]{REGEX};
    }

    @Override
    public String build(ConditionInstance conditionInstance) {
        try {
            String referenceValue = conditionInstance.getReferenceValue();
            if (!referenceValue.startsWith("/")) {
                referenceValue = "/" + referenceValue;
            }
            if (!referenceValue.endsWith("/")) {
                referenceValue = referenceValue + "/";
            }
            return String.format(format(conditionInstance), conditionInstance.getVariableName(), referenceValue);
        } catch (Exception e) {
            throw new RuleRunTimeException(e);
        }
    }
}