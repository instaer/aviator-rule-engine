package com.github.instaer.ruleengine.rule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;

@Table(name = "t_ruleset_info")
@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RulesetInfoEntity {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * ruleset unique code
     */
    @Column
    private String code;

    /**
     * ruleset name
     */
    @Column
    private String name;

    /**
     * ruleset remark
     */
    @Column
    private String remark;

    /**
     * default return values collection (json object string)
     * e.g. {"variable1":value1,"variable2":value2}
     */
    @Column
    private String defaultReturnValues;

    /**
     * ruleset expression
     */
    @Column
    private String expression;

    /**
     * ruleset mode {@code RulesetMode}
     */
    @Generated(GenerationTime.INSERT)
    @Column
    private Integer mode;
}