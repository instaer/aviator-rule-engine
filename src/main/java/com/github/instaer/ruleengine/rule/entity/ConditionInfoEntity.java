package com.github.instaer.ruleengine.rule.entity;

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
     * id of the rule it belongs to
     */
    private Long ruleId;

    /**
     * condition name
     */
    @Column
    private String name;

    /**
     * condition remark
     */
    @Column
    private String remark;

    /**
     * variable name (a unique identifier passed in as a variable)
     */
    @Column
    private String variableName;

    @Column
    private String referenceValue;

    /**
     * condition relational operation type {@code ConditionRelationType}
     */
    @Column
    private Integer relationType;

    /**
     * condition logical operation type {@code ConditionLogicType}
     */
    @Column
    private Integer logicType;

    /**
     * condition priority (the larger the value, the higher the priority)
     */
    @Column
    private Integer priority;
}