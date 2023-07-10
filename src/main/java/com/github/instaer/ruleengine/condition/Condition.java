package com.github.instaer.ruleengine.condition;

import com.github.instaer.ruleengine.constants.ConditionRelationType;

public interface Condition {

    default String format(ConditionInstance conditionInstance) {
        return conditionInstance.getRelationType().getValue();
    }

    ConditionRelationType[] relationTypes();

    String build(ConditionInstance conditionInstance);
}