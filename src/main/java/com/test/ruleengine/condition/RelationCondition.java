package com.test.ruleengine.condition;

import com.test.ruleengine.constants.ConditionOperateType;
import com.test.ruleengine.exception.RuleRunTimeException;
import org.springframework.stereotype.Component;

@Component
public class RelationCondition implements Condition {

    @Override
    public ConditionOperateType[] operateTypes() {
        return new ConditionOperateType[]{
                ConditionOperateType.EQUAL,
                ConditionOperateType.NOT_EQUAL,
                ConditionOperateType.LESS_EQUAL,
                ConditionOperateType.GREATER_EQUAL,
                ConditionOperateType.LESS,
                ConditionOperateType.GREATER
        };
    }

    @Override
    public String buildCondition(ConditionInstance conditionInstance) {
        try {
            return String.format(format(conditionInstance), conditionInstance.getVariableName(), conditionInstance.getReferenceValue());
        } catch (Exception e) {
            throw new RuleRunTimeException(e);
        }
    }
}