# aviator-rule-engine
一个基于[AviatorScript](https://github.com/killme2008/aviatorscript)的简单规则引擎实例。

将规则划分为规则集、规则、条件三个维度，可以满足业务规则的通用性配置，同时也可以进行扩展。

- [<u>**快速使用示例入口**</u>](#使用示例)  
- [<u>**快速使用说明入口**</u>](#使用说明)  
- [<u>**快速查看接口文档**</u>](./README_API.md)

## 依赖版本
- AviatorScript 5.3.0+
- SpringBoot 2.0+

## 创建数据表
<details>
  <summary>条件(t_condition_info)</summary>

  ```mysql
CREATE TABLE `t_condition_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `rule_id` bigint unsigned NOT NULL COMMENT '所属规则id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '条件名称',
  `remark` varchar(128) DEFAULT NULL COMMENT '条件备注',
  `variable_name` varchar(32) NOT NULL COMMENT '变量名（作为参数传入的唯一标识符）',
  `reference_value` varchar(256) NOT NULL COMMENT '参考值',
  `relation_type` varchar(32) NOT NULL COMMENT '条件关系运算类型',
  `logic_type` varchar(32) NOT NULL COMMENT '条件逻辑运算类型',
  `priority` smallint unsigned NOT NULL COMMENT '条件优先级（值越大优先级越高）',
  PRIMARY KEY (`id`),
  KEY `ix_rule_id` (`rule_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '条件';
  ```
</details>

<details>
  <summary>规则(t_rule_info)</summary>

  ```mysql
CREATE TABLE `t_rule_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `ruleset_id` bigint NOT NULL COMMENT '所属规则集ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规则名称',
  `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '规则备注',
  `return_values` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返回值集合',
  `logic_type` varchar(32) NOT NULL COMMENT '规则逻辑运算类型',
  `priority` smallint unsigned NOT NULL COMMENT '规则优先级（值越大优先级越高）',
  PRIMARY KEY (`id`),
  KEY `ix_ruleset_id` (`ruleset_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '规则';
  ```
</details>

<details>
  <summary>规则集(t_ruleset_info)</summary>

  ```mysql
CREATE TABLE `t_ruleset_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规则集编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规则集名称',
  `remark` varchar(128) DEFAULT NULL COMMENT '规则集备注',
  `default_return_values` varchar(256) DEFAULT NULL COMMENT '默认返回值集合',
  `expression` varchar(1024) DEFAULT NULL COMMENT '规则集表达式',
  `mode` tinyint NOT NULL DEFAULT '0' COMMENT '模式（0：创建中  1：已创建）',
  PRIMARY KEY (`id`),
  KEY `ix_code` (`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '规则集';
  ```
</details>

## 支持的条件逻辑运算类型
[ConditionLogicType](https://github.com/instaer/aviator-rule-engine/blob/master/src/main/java/com/github/instaer/ruleengine/constants/ConditionLogicType.java)

| 逻辑类型 | 值 |               描述               |
| :---: | :---: | :------------------------------: |
|  AND  |  &&   | 如果当前条件单元为真，则继续向后执行，否则跳过后面的条件单元，直接返回假 |
|  OR   | \|\|  |   如果当前条件单元为真，则跳过后面的条件单元直接返回真，否则继续向后执行   |

## 支持的条件关系运算类型
[ConditionRelationType](https://github.com/instaer/aviator-rule-engine/blob/master/src/main/java/com/github/instaer/ruleengine/constants/ConditionRelationType.java)

* 关系类型条件

|   条件类型   |      描述      |
| :-----------: | :------------: |
|     EQUAL     |   等于（=）    |
|   NOT_EQUAL   |  不等于（!=）  |
|  LESS_EQUAL   | 小于等于（<=） |
| GREATER_EQUAL | 大于等于（>=） |
|     LESS      |   小于（<）    |
|    GREATER    |   大于（>）    |

* 集合类型条件

|       条件类型       |  描述  |
| :-------------------: | :----: |
|    INCLUDE_IN_LIST    |  列表包含指定字符串  |
|  NOT_INCLUDE_IN_LIST  | 列表不包含指定字符串 |
| SOME_CONTAINS_IN_LIST |  列表包含指定字符串的某一部分  |
| NONE_CONTAINS_IN_LIST | 列表不包含指定字符串的任何部分 |

> **提示:** 列表作为参考值(`referenceValue`)传入时，多个元素之间以逗号(`,`)进行分隔。
>
> 例如：`1,2,3`，`字符1,字符2,字符3`。

* 字符类型条件

|       条件类型       |  描述  |
| :-------------------: | :----: |
|    STRING_CONTAINS    |  包含指定字符  |
|  STRING_NOT_CONTAINS  | 不包含指定字符 |
| STRING_STARTSWITH |  以指定字符开始  |
| STRING_NOT_STARTSWITH | 不以指定字符开始 |
| STRING_ENDSWITH |  以指定字符结束  |
| STRING_NOT_ENDSWITH | 不以指定字符结束 |

* 区间类型条件

|       条件类型       |  描述  |
| :-------------------: | :----: |
|    INTERVAL_NUMBER    |  数值区间  |
|  INTERVAL_STRING_LENGTH  | 字符长度区间 |

> **提示:** 区间作为参考值(`referenceValue`)传入时，和数学中的区间表示形式和含义相同。
>
> 例如：`[1,10]`是闭区间，表示：`x >= 1 && x <= 10`；`(1,10)`是开区间，表示：`x > 1 && x < 10`；`[1,10)`是左闭右开区间，表示：`x >= 1 && x < 10`；`(1,10]`是左开右闭区间，表示：`x > 1 && x <= 10`。

* 正则类型条件

| 条件类型 | 描述 |
| :-------: | :--: |
|  REGEX  | 正则 |

> **提示:** 正则表达式作为参考值(`referenceValue`)传入时，语法和Java完全一致。但形式上有略微差异，AviatorScript中的正则表达式需要以`\`括起来，并且对于需要转义的字符不需要连续的反斜杠`\\` ，只要一个`\` 即可。
>
> 此处做了兼容，如果参考值配置的正则表达式首尾不包含`\`时，将自动添加。

## 支持的规则逻辑运算类型
[RuleLogicType](https://github.com/instaer/aviator-rule-engine/blob/master/src/main/java/com/github/instaer/ruleengine/constants/RuleLogicType.java)

| 逻辑类型 | 值 |               描述               |
| :---: | :---: | :------------------------------: |
|  AND  |  &&   | 关联类型：在优先级相邻的两个规则中，优先级较高的规则和优先级较低的规则同时匹配时，才返回对应的返回值。否则返回null或规则集设置的默认返回值（如果已设置）。 |
|  XOR   |   |   互斥类型：在优先级相邻的两个规则中，优先级较高的规则匹配时，直接返回对应的返回值，不再验证后面的规则；否则，验证优先级较低的规则是否满足，如果满足则返回对应的返回值。不满足则返回null或规则集设置的默认返回值（如果已设置）。   |

## 条件
条件是最小的执行单元，例如：`x >= 99`。

多个条件可以按照关系运算类型、逻辑运算类型组合成一个规则。例如：`x >= 99 || y < 45`。

当然单个条件本身也是一个规则，可以认为`x >= 99`等同于`x >= 99 && true`。

### 条件优先级
条件优先级决定了条件之间的组合次序和执行顺序。同一个规则下所有条件按优先级进行排序，优先级越高，组合次序和执行顺序越靠前。（同一规则下，不能存在优先级相同的条件，保证执行顺序唯一。）

例如，定义一个规则下的条件表达式及优先级如下：

| 条件表达式 | 条件逻辑运算符 | 条件优先级 |
| :--------: | :------------: | :--------: |
|   x == 1   |      \|\|      |     8      |
|   y > 2    |       &&       |     4      |
|   z <= 3   |       &&       |     2      |
|   v != 4   |      \|\|      |     1      |

最后生成的规则表达式为：
> ((x ==1 || y>2) && z <= 3) && v != 4

同时注意，在每两个条件的组合两侧添加了括号，保证了括号内条件的优先级。

### 条件逻辑运算
每个条件都附带[逻辑运算类型](#支持的条件逻辑运算类型)，当两个条件之间进行组合时，高优先级条件的逻辑运算作用于低优先级条件，而低优先级条件的逻辑运算作用于条件组合整体。

以如下表达式为例：

>`((x == 1 || y > 2) && z <= 3) && v != 4`

* 条件`x == 1`逻辑运算类型为`||`，作用于条件`y > 2`。
* 条件`y > 2`逻辑运算类型为`&&`，由于和左侧条件`x == 1`进行组合，所以作用于右侧条件`z <= 3`。
* 条件`z <= 3`逻辑运算类型为`&&`，由于和左侧条件组合`(x == 1 || y> 2)`进行组合，所以作用于右侧条件`v != 4`。
* 条件`v != 4`优先级最低，在生成规则表达式时将会自动忽略其逻辑运算类型。
* 假设条件`v != 4`逻辑运算类型为`||`，则整个表达式等价于`(((x == 1 || y > 2) && z <= 3) && v != 4) || false`；假设条件`v != 4`逻辑运算类型为`&&`，则整个表达式等价于`(((x == 1 || y > 2) && z <= 3) && v != 4) && true`。

### 条件关系运算
每个条件都附带[条件关系类型](#支持的条件关系运算类型)，用于和参考值进行比较。

例如条件`x >= 99`，`x`是条件的变量名，作为参数传入的唯一标识符，`>=`是条件的关系运算符（对应的关系运算类型为，`ConditionRelationType.EQUAL`），`99`是条件的参考值，用于和以`x`为变量名的参数值进行对应的关系运算。

## 规则
多个条件经过组合之后就是一个规则，一个规则下附带一个条件列表，在生成规则表达式时，条件列表中的所有条件将按优先级从高到低进行组合。

### 规则优先级
规则优先级的含义和[条件优先级](#条件优先级)相似。规则优先级决定了多个规则之间的组合顺序和结构。

### 规则逻辑运算
每条规则都附带[逻辑运算类型](#支持的规则逻辑运算类型)，以两个规则的组合为例，规则逻辑运算的作用机制说明如下：
1. 若高优先级规则的逻辑运算类型为关联类型（`AND`），则高优先级规则的逻辑运算作用于低优先级规则，低优先级规则的逻辑运算不影响规则组合，但低优先级规则设置的返回值集合即为规则组合的返回值集合（高优先级规则设置的返回值集合被覆盖）；
2. 若高优先级规则的逻辑运算类型为互斥类型（`XOR），则高优先级规则和低优先级规则的逻辑运算互不影响，返回值集合也不会被任何一方覆盖；

以如下表达式为例：

>`(a == 1|| b == 2) && (c == 3 || d == 4)`

从表面上看，这是一个规则表达式，将其拆分为4个条件（最小执行单元）后分别为`a == 1`、`b ==2`、`c ==3`、`d ==4`，同一规则下的多个条件组合时按照优先级从高到低进行组合，组合顺序是唯一的。但结合表达式来看，此处的表达式出现了两个组合顺序，即：`(a == 1|| b == 2)`和`(c == 3 || d == 4)`，因此不能作为一条规则进行组合。

我们将其作为两个规则（R1，R2）进行组合，这两条规则下的条件列表分别为`a == 1`、`b ==2`和`c ==3`、`d ==4`，对应的两个规则表达式为R1:`a == 1|| b == 2，R2:c == 3 || d == 4`。

其中R1优先级设置为100，逻辑运算类型设置为关联类型（`AND`），返回值集合和R2相同即可（R1在两个规则的组合之间优先级最高，返回值集合被R2覆盖）；

R2优先级设置为98，逻辑运算类型为AND和XOR均可（R2在两个规则的组合之间优先级最低，逻辑运算不作用于其他任何规则），返回值集合设置为：`{'myvariable':true}`。

将R1、R2添加到一个规则集下，最终生成表达式为`(a == 1|| b == 2) && (c == 3 || d == 4)`，对应生成的aviator执行脚本为：
```
let rmap = seq.map('myvariable', false);
if((a == 1 || b == 2) && (c == 3 || d == 4)){
seq.put(rmap, 'myvariable', true);
}
return rmap;
```

## 规则集
规则集是规则引擎执行的对象，一个规则集下包含一个或多个规则，默认添加的规则集处于`RulesetMode.BUILDING`模式，表示当前的规则集表达式未生成，规则集处于不可用状态。当规则集下存在规则，并且每个规则下存在条件时，规则集自动切换为`RulesetMode.BUILT`模式，并且生成规则集表达式。

### 规则集设置默认返回值集合
规则集支持设置默认返回值。在设置规则时，可以设置当前规则匹配时的返回值，对于规则集下所有规则均不匹配的情况，可以在规则集设置默认返回值`defaultReturnValues`，数据格式和在规则下设置返回值集合相同（`e.g. {'returnVariable1':returnValue1,'returnVariable2':returnValue2}`）。

## 使用示例

### 一个计算费用、费率的简单示例

如下为一个产品费率计算规则：

| 年龄（AGE） | 额度（AMT） | 费用（PREM） | 费率（RATE）‰ |
| :---------: | :---------: | :----------: | :-----------: |
|    0~3岁    |   1000000   |     67.9     |    0.0679     |
|   4~11岁    |   1000000   |    198.2     |    0.1982     |
|    12岁     |   1000000   |      18      |     0.018     |

需要按上述规则，通过年龄、额度来动态计算0~12岁的儿童产品的费用和费率。

* 添加规则集

指定规则集编码为`RULEST_RATE_CALC`，参数如下：
```json
{
    "code": "RULEST_RATE_CALC",
    "name": "ruleset for rate calculate",
    "defaultReturnValues": "{PREM:0.00,RATE:0.00}"
}
```

* 在当前规则集下添加规则

定义返回值集合，用于接收规则引擎执行的返回值 ，例如`{PREM:67.9,RATE:0.0679}`， 含义就是如果满足规则对应的条件，就返回一个map集合：
```json
{
    "PREM": 67.9,
    "RATE": 0.0679
}
```
通过返回值集合中定义的返回值变量名`PREM`和`RATE`，可以从map集合中获取对应的值。

在当前例子中，需要添加三组规则，参数如下：
```json
{
    "rulesetId": 2,
    "name": "rule for age(0-3)",
    "returnValues": "{PREM:67.9,RATE:0.0679}",
    "logicType": "XOR",
    "priority": 100
}

{
    "rulesetId": 2,
    "name": "rule for age(4-11)",
    "returnValues": "{PREM:198.2,RATE:0.1982}", 
    "logicType": "XOR", 
    "priority": 99
}

{
    "rulesetId": 2,
    "name": "rule for age(12)",
    "returnValues": "{PREM:18,RATE:0.018}",
    "logicType": "XOR",
    "priority": 98
}
```

* 在每个对应规则下添加条件

<u><b>rule for age(0-3)</b></u>
```json
[
    {
        "ruleId": 2,
        "name": "children age 0~3",
        "variableName": "AGE",
        "referenceValue": "[0,3]",
        "relationType": "INTERVAL_NUMBER",
        "logicType": "AND",
        "priority": 10
    },
    {
        "ruleId": 2,
        "name": "amount total",
        "variableName": "AMT",
        "referenceValue": "1000000",
        "relationType": "EQUAL",
        "logicType": "AND",
        "priority": 8
    }
]
```

<u><b>rule for age(4-11)</b></u>
```json
[
    {
        "ruleId": 4,
        "name": "children age 4~11",
        "variableName": "AGE",
        "referenceValue": "[4,11]",
        "relationType": "INTERVAL_NUMBER",
        "logicType": "AND",
        "priority": 10
    },
    {
        "ruleId": 4,
        "name": "amount total",
        "variableName": "AMT",
        "referenceValue": "1000000",
        "relationType": "EQUAL",
        "logicType": "AND",
        "priority": 8
    }
]
```

<b><u>rule for age(12)</u></b>
```json
[
    {
        "ruleId": 5,
        "name": "children age",
        "variableName": "AGE",
        "referenceValue": "12",
        "relationType": "EQUAL",
        "logicType": "AND",
        "priority": 10
    },
    {
        "ruleId": 5,
        "name": "amount total",
        "variableName": "AMT",
        "referenceValue": "1000000",
        "relationType": "EQUAL",
        "logicType": "AND",
        "priority": 9
    }
]
```

* 生成规则表达式

添加规则集、规则、条件之后，自动生成规则表达式如下：
```
let rmap = seq.map();
if((AGE >= 0 && AGE <= 3) && AMT == 1000000){
seq.put(rmap, 'PREM', 67.9);
seq.put(rmap, 'RATE', 0.0679);
}
elsif((AGE >= 4 && AGE <= 11) && AMT == 1000000){
seq.put(rmap, 'PREM', 198.2);
seq.put(rmap, 'RATE', 0.1982);
}
elsif(AGE == 12 && AMT == 1000000){
seq.put(rmap, 'PREM', 18);
seq.put(rmap, 'RATE', 0.018);
}
return rmap;
```

另外，对于所有浮点数类型的入参和出参，在AviatorScript实际执行上述表达式时，会将所有浮点数都解析为`decimal`，保证高精度运算的要求。（在默认配置的引擎实例中已开启此配置项`Options.ALWAYS_PARSE_FLOATING_POINT_NUMBER_INTO_DECIMAL`）。

* 执行规则集

假设现在要计算年龄为9岁的儿童，额度为1000000时的费用和费率，规则引擎请求参数为：
```json
{
    "rulesetCode": "RULEST_RATE_CALC",
    "paraMap": {
        "AGE": 9,
        "AMT": 1000000
    }
}
```

规则引擎返回结果：
```json
{
    "status": 200,
    "success": true,
    "message": null,
    "body": {
        "RATE": 0.1982,
        "PREM": 198.2
    }
}
```

* 接口调用

在服务中，调用接口方式如下：
```java
@Autowired
private RuleCoreService ruleCoreService;

Map<String, Object> paraMap = new HashMap<>();
paraMap.put("AGE", 9);
paraMap.put("AMT", 1000000);
String rulesetCode = "RULEST_RATE_CALC";
Map<String, Object> resultMap = ruleCoreService.executeRuleset(rulesetCode, paraMap);

System.out.println("费用：" + resultMap.get("PREM"));// 198.2
System.out.println("费率：" + resultMap.get("RATE"));// 0.1982
```

或者通过外部接口调用`/api/executeRuleset`。

## Aviator编译模式
默认配置的Aviator执行器实例(`AviatorEvaluatorInstance`)设置的是编译缓存模式，避免在每次执行规则集时都对表达式重新编译，影响性能。

同时也使用了LRU缓存(Aviator 5.0+)，用于指定缓存的规则集表达式数量，默认是500，可以在配置文件中对属性(`aviator.expression-cache-capacity`)进行配置。

## 使用说明
建议将本项目部署为独立的服务。数据库及相关服务配置请修改[配置文件](https://github.com/instaer/aviator-rule-engine/blob/master/src/main/resources/application.properties) 。

在服务部署前，请在配置的数据库中[创建数据表](#创建数据表) 。

目前规则维护仅提供[内部管理接口](./README_API.md#内部管理接口)，建议按照：**规则集**->**规则**->**条件**的顺序进行配置。
