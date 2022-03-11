package com.test.ruleengine.rule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

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
     * 规则编码（唯一 用于调用方进行绑定）
     */
    @Column
    private String code;

    /**
     * 规则名称
     */
    @Column
    private String name;

    /**
     * 规则备注
     */
    @Column
    private String remark;

    /**
     * 规则表达式
     */
    @Column
    private String expression;

    /**
     * 模式（1：规则已设置  2：表达式已设置）
     */
    @Generated(GenerationTime.INSERT)
    @Column
    private Integer mode;

    /**
     * 状态（1：启用  0：禁用）
     */
    @Generated(GenerationTime.INSERT)
    @Column
    private Integer status;
}