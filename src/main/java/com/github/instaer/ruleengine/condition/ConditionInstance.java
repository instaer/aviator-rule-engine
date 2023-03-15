package com.github.instaer.ruleengine.condition;

import com.github.instaer.ruleengine.constants.ConditionLogicType;
import com.github.instaer.ruleengine.constants.ConditionRelationType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConditionInstance {

    /**
     * variable name (a unique identifier passed in as a variable)
     */
    private String variableName;

    /**
     * condition relational operation type (using between variables and reference values)
     */
    private ConditionRelationType relationType;

    private String referenceValue;

    /**
     * condition priority (the larger the value, the higher the priority)
     */
    private int priority;

    /**
     * Condition logical operation type (using between conditions with similar priorities)
     */
    private ConditionLogicType logicType;

}