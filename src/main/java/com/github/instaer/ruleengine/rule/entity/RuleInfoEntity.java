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
     * Return values collection (comma-separated key-value pairs)
     * e.g. returnVariable1:returnValue1,returnVariable2:returnValue2
     */
    @Column
    private String returnValues;
}