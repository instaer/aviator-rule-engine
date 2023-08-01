package com.github.instaer.ruleengine.common.entity;

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
     * variable name (unique identifier for passing in parameters)
     */
    @Column
    private String variableName;

    @Column
    private String referenceValue;

    /**
     * condition relation operator type {@code ConditionRelationType}
     */
    @Column
    private String relationType;

    /**
     * condition logic operator type {@code ConditionLogicType}
     */
    @Column
    private String logicType;

    /**
     * condition priority (the larger the value, the higher the priority)
     */
    @Column
    private Integer priority;
}