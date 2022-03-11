package com.test.ruleengine.condition;

import com.test.ruleengine.constants.ConditionLogicType;
import com.test.ruleengine.constants.ConditionOperateType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConditionInstance {

    /**
     * 变量名（作为变量传入的唯一标识符）
     */
    private String variableName;

    /**
     * 条件运算类型（适用于变量与参考值之间）
     */
    private ConditionOperateType operateType;

    /**
     * 参考值
     */
    private String referenceValue;

    /**
     * 条件优先级（值越大优先级越高）
     */
    private int priority;

    /**
     * 条件逻辑类型（适用于优先级相近的条件之间）
     */
    private ConditionLogicType logicType;

}