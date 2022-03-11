package com.test.ruleengine.condition;

import com.test.ruleengine.constants.ConditionOperateType;
import com.test.ruleengine.exception.RuleRunTimeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class SequenceCondition implements Condition {

    @Override
    public ConditionOperateType[] operateTypes() {
        return new ConditionOperateType[]{
                ConditionOperateType.INCLUDE_IN_LIST,
                ConditionOperateType.NOT_INCLUDE_IN_LIST,
                ConditionOperateType.SOME_CONTAINS_IN_LIST,
                ConditionOperateType.NONE_CONTAINS_IN_LIST
        };
    }

    @Override
    public String buildCondition(ConditionInstance conditionInstance) {
        try {
            String[] elements = conditionInstance.getReferenceValue().split(",");
            for (int i = 0; i < elements.length; i++) {
                elements[i] = "'" + elements[i] + "'";
            }
            String valueSet = StringUtils.join(elements, ",");
            return String.format(format(conditionInstance), valueSet, conditionInstance.getVariableName());
        } catch (Exception e) {
            throw new RuleRunTimeException(e);
        }
    }
}