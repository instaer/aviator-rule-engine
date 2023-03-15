package com.github.instaer.ruleengine.condition;

import com.github.instaer.ruleengine.constants.ConditionRelationType;
import com.github.instaer.ruleengine.exception.RuleRunTimeException;
import org.springframework.stereotype.Component;

import static com.github.instaer.ruleengine.constants.ConditionRelationType.*;

@Component
public class RelationCondition implements Condition {

    @Override
    public ConditionRelationType[] relationTypes() {
        return new ConditionRelationType[]{
                EQUAL,
                NOT_EQUAL,
                LESS_EQUAL,
                GREATER_EQUAL,
                LESS,
                GREATER
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