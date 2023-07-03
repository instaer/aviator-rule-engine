package com.github.instaer.ruleengine.condition;

import com.github.instaer.ruleengine.constants.ConditionRelationType;
import com.github.instaer.ruleengine.exception.RuleRunTimeException;
import org.springframework.stereotype.Component;

import static com.github.instaer.ruleengine.constants.ConditionRelationType.*;

@Component
public class StringCondition implements Condition {

    @Override
    public ConditionRelationType[] relationTypes() {
        return new ConditionRelationType[]{
                STRING_CONTAINS,
                STRING_NOT_CONTAINS,
                STRING_STARTSWITH,
                STRING_NOT_STARTSWITH,
                STRING_ENDSWITH,
                STRING_NOT_ENDSWITH
        };
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
