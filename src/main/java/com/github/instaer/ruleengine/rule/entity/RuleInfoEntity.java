package com.github.instaer.ruleengine.rule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name = "t_rule_info")
@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleInfoEntity {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * id of the ruleset it belongs to
     */
    @Column
    private Long rulesetId;

    /**
     * rule name
     */
    @Column
    private String name;

    /**
     * rule remark
     */
    @Column
    private String remark;

    /**
     * Return values collection (json object string)
     * e.g. {"variable1":value1,"variable2":value2}
     */
    @Column
    private String returnValues;

    /**
     * rule logic operator type {@code RuleLogicType}
     */
    @Column
    private String logicType;

    /**
     * rule priority (the larger the value, the higher the priority)
     */
    @Column
    private Integer priority;
}