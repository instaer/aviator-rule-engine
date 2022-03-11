# aviator-rule-engine
基于AviatorScript的规则引擎实例



## Requirements
- Java 8.0+

- Spring Boot 2.0+

- MySQL 5.5+

- AviatorScript 5.3.0+



## Tables Required

t_rule_info

```mysql
CREATE TABLE `t_rule_info` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '规则id',
  `code` VARCHAR(20) NOT NULL COMMENT '规则编码（唯一 用于调用方进行绑定）',
  `name` VARCHAR(20) NOT NULL COMMENT '规则名称',
  `remark` VARCHAR(128) NULL COMMENT '规则备注',
  `expression` VARCHAR(1024) NULL COMMENT '规则表达式',
  `mode` TINYINT(4) UNSIGNED NOT NULL DEFAULT 1 COMMENT '模式（1：规则已设置  2：表达式已设置）',
  `status` TINYINT(4) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（1：启用  0：禁用）',
  PRIMARY KEY (`id`),
	KEY `ix_code` (`code`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '规则信息表';
```

t_condition_info

```mysql
CREATE TABLE `t_condition_info` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '条件id',
  `rule_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '所属规则id',
  `name` VARCHAR(20) NOT NULL COMMENT '条件名称',
  `remark` VARCHAR(128) NULL COMMENT '条件备注',
  `variable_name` varchar(32) NOT NULL COMMENT '变量名（作为变量传入的唯一标识符）',
   `reference_value` varchar(256) NOT NULL COMMENT '参考值',
  `operate_type` TINYINT(4) UNSIGNED NOT NULL COMMENT '条件运算类型',
  `logic_type` TINYINT(4) UNSIGNED NOT NULL COMMENT '条件逻辑类型（1-当前条件满足则继续，否则跳过后续条件 2-当前条件满足则跳过后续条件，否则继续）',
  `priority` SMALLINT(15) UNSIGNED NOT NULL COMMENT '条件优先级（值越大优先级越高）',
  PRIMARY KEY (`id`),
  KEY `ix_rule_id` (`rule_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '条件信息表';
```



## Getting started

```java
@Autowired
private RuleCoreService ruleCoreService;

String ruleCode = "BUSINESS_RULE_001";
Map<String, Object> paraMap = new HashMap<>();
paramMap.put("var1", "value");
boolean result = ruleCoreService.executeRule(ruleCode, paramMap);
```