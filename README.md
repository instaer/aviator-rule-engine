# aviator-rule-engine
基于AviatorScript的规则引擎实例

## Requirements
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

## 支持的逻辑类型
https://github.com/instaer/aviator-rule-engine/blob/e4f20a81977c7368dd926cdef7b2390de916f140/src/main/java/com/test/ruleengine/constants/ConditionLogicType.java

| LOGIC | VALUE |               DESC               |
| :---: | :---: | :------------------------------: |
|  AND  |  &&   | 当前条件必须满足，否则规则不通过 |
|  OR   | \|\|  |   当前条件如果满足，则规则通过   |

## 支持的条件类型
https://github.com/instaer/aviator-rule-engine/blob/e4f20a81977c7368dd926cdef7b2390de916f140/src/main/java/com/test/ruleengine/constants/ConditionOperateType.java

* 关系型条件

|   CONDITION   |      DESC      |
| :-----------: | :------------: |
|     EQUAL     |   等于（=）    |
|   NOT_EQUAL   |  不等于（!=）  |
|  LESS_EQUAL   | 小于等于（<=） |
| GREATER_EQUAL | 大于等于（>=） |
|     LESS      |   小于（<）    |
|    GREATER    |   大于（>）    |

* 集合型条件

|       CONDITION       |  DESC  |
| :-------------------: | :----: |
|    INCLUDE_IN_LIST    |  包括  |
|  NOT_INCLUDE_IN_LIST  | 不包括 |
| SOME_CONTAINS_IN_LIST |  包含  |
| NONE_CONTAINS_IN_LIST | 不包含 |

* 正则型条件

| CONDITION | DESC |
| :-------: | :--: |
|  REGULAR  | 正则 |

## Getting started
### Example
在规则管理中添加一条规则，规则编码定义为BUSINESS_RULE_001，在规则下添加以下两个条件。

* 条件1
定义条件变量名为price，指定一个参考值，例如49.99，同时指定条件运算类型为GREATER_EQUAL（>=），逻辑运算类型为AND（&&）；
* 条件2
定义条件变量名为discount，指定一个参考值，例如0.85，同时指定条件运算类型为LESS（<），逻辑运算类型为AND（&&）（最后一个条件定义的逻辑运算类型将自动忽略）。

最终生成条件表达式为：
(price >= 49.99 && discount < 0.85)

从外部调用中，传入price = 50，discount = 0.75，计算表达式值为true。

```java
@Autowired
private RuleCoreService ruleCoreService;

String ruleCode = "BUSINESS_RULE_001";
Map<String, Object> paraMap = new HashMap<>();
paramMap.put("price", 50);
paramMap.put("discount", 0.75);
boolean result = ruleCoreService.executeRule(ruleCode, paramMap);
```
