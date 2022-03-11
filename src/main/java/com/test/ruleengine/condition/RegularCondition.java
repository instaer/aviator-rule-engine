package com.test.ruleengine.condition;

import com.test.ruleengine.constants.ConditionOperateType;
import com.test.ruleengine.exception.RuleRunTimeException;
import org.springframework.stereotype.Component;

@Component
public class RegularCondition implements Condition {

    @Override
    public ConditionOperateType[] operateTypes() {
        return new ConditionOperateType[]{ConditionOperateType.REGULAR};
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