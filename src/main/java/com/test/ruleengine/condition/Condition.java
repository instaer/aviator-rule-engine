package com.test.ruleengine.condition;

import com.test.ruleengine.constants.ConditionOperateType;

public interface Condition {

    default String format(ConditionInstance conditionInstance) {
        return conditionInstance.getOperateType().getFormat();
    }

    ConditionOperateType[] operateTypes();

    String buildCondition(ConditionInstance conditionInstance);
}