package com.test.ruleengine.rule.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name = "t_condition_info")
@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ConditionInfoEntity {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 所属规则ID
     */
    private Long ruleId;

    /**
     * 条件名称
     */
    @Column
    private String name;

    /**
     * 条件备注
     */
    @Column
    private String remark;

    /**
     * 变量名（作为变量传入的唯一标识符）
     */
    @Column
    private String variableName;

    /**
     * 参考值
     */
    @Column
    private String referenceValue;

    /**
     * 条件运算类型{@code ConditionOperateType}
     */
    @Column
    private Integer operateType;

    /**
     * 条件逻辑类型{@code ConditionLogicType}
     */
    @Column
    private Integer logicType;

    /**
     * 条件优先级（值越大优先级越高）
     */
    @Column
    private Integer priority;
}