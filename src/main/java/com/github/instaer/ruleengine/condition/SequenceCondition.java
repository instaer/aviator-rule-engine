package com.github.instaer.ruleengine.condition;

import com.github.instaer.ruleengine.constants.ConditionRelationType;
import com.github.instaer.ruleengine.exception.RuleRunTimeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static com.github.instaer.ruleengine.constants.ConditionRelationType.*;

@Component
public class SequenceCondition implements Condition {

    @Override
    public ConditionRelationType[] relationTypes() {
        return new ConditionRelationType[]{
                INCLUDE_IN_LIST,
                NOT_INCLUDE_IN_LIST,
                SOME_CONTAINS_IN_LIST,
                NONE_CONTAINS_IN_LIST
        };
    }

    @Override
    public String build(ConditionInstance conditionInstance) {
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