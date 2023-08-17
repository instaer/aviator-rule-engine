# 接口文档
本项目中主要有两类接口：外部请求接口和内部管理接口。

## 外部请求接口

<details open>
  <summary><b>执行规则集</b></summary>

#### Request
- Method: **POST**
- URL:  ```/api/rulesetInfo/execute```
- Headers:  Content-Type: application/json
- Body:
```
{
  "code":"RULESET_EXAMPLE",
  "paraMap":{
    "AGE":19,
    "MOBILE":"18966666666"
  }
}
```

#### Response
- Headers:  Content-Type: application/json
- Body
```
{
    "status": 200,
    "success": true,
    "message": null,
    "body": {
        "myvalue": "校验通过"
    }
}
```
</details>

## 内部管理接口

<details>
  <summary><b>查询条件逻辑运算类型</b></summary>

#### Request
- Method: **GET**
- URL:  ```/admin/conditionLogicType/get```
- Headers:  Content-Type: application/x-www-form-urlencoded
- Body:
```
```

#### Response
- Headers:  Content-Type: application/json
- Body
```
{
    "status": 200,
    "success": true,
    "message": null,
    "body": {
        "OR": "OR",
        "AND": "AND"
    }
}
```
</details>

<details>
  <summary><b>查询条件关系运算类型</b></summary>

#### Request
- Method: **GET**
- URL:  ```/admin/conditionRelationType/get```
- Headers:  Content-Type: application/x-www-form-urlencoded
- Body:
```
```

#### Response
- Headers:  Content-Type: application/json
- Body
```
{
    "status": 200,
    "success": true,
    "message": null,
    "body": {
        "REGEX": "REGEX",
        "GREATER_EQUAL": "GREATER EQUAL",
        "EQUAL": "EQUAL",
        "GREATER": "GREATER",
        "NOT_EQUAL": "NOT EQUAL",
        "NONE_CONTAINS_IN_LIST": "NONE CONTAINS IN LIST",
        "SOME_CONTAINS_IN_LIST": "SOME CONTAINS IN LIST",
        "LESS": "LESS",
        "LESS_EQUAL": "LESS EQUAL",
        "NOT_INCLUDE_IN_LIST": "NOT INCLUDE IN LIST",
        "INCLUDE_IN_LIST": "INCLUDE IN LIST"
    }
}
```
</details>

<details>
  <summary><b>查询规则逻辑运算类型</b></summary>

#### Request
- Method: **GET**
- URL:  ```/admin/ruleLogicType/get```
- Headers:  Content-Type: application/x-www-form-urlencoded
- Body:
```
```

#### Response
- Headers:  Content-Type: application/json
- Body
```
{
    "status": 200,
    "success": true,
    "message": null,
    "body": {
        "AND": "AND",
        "XOR": "XOR"
    }
}
```
</details>

<details>
  <summary><b>刷新规则集</b></summary>

#### Request
- Method: **POST**
- URL:  ```/admin/rulesetInfo/refresh```
- Headers:  Content-Type: application/json
- Body:
```
{
  "id":5
}
```

#### Response
- Headers:  Content-Type: application/json
- Body
```
{
    "status": 200,
    "success": true,
    "message": null,
    "body": null
}
```
</details>

<details>
  <summary><b>查询规则集（分页）</b></summary>

#### Request
- Method: **GET**
- URL:  ```/admin/rulesetInfo/query?page=0&size=10&code=RULESET_EXAMPLE```
- Headers:  Content-Type: application/x-www-form-urlencoded
- Body:
```
```

#### Response
- Headers:  Content-Type: application/json
- Body
```
{
    "status": 200,
    "success": true,
    "message": null,
    "body": {
        "content": [
            {
                "id": 5,
                "code": "RULESET_EXAMPLE",
                "name": "ruleset example",
                "remark": null,
                "defaultReturnValues": "{'myvalue':'校验通过'}",
                "expression": null,
                "mode": 0
            }
        ],
        "pageable": {
            "sort": {
                "sorted": true,
                "unsorted": false,
                "empty": false
            },
            "offset": 0,
            "pageSize": 10,
            "pageNumber": 0,
            "paged": true,
            "unpaged": false
        },
        "totalElements": 1,
        "last": true,
        "totalPages": 1,
        "size": 10,
        "number": 0,
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "numberOfElements": 1,
        "first": true,
        "empty": false
    }
}
```
</details>

<details>
  <summary><b>查询规则集详情</b></summary>

#### Request
- Method: **GET**
- URL:  ```/admin/rulesetInfo/detail?code=RULESET_EXAMPLE```
- Headers:  Content-Type: application/x-www-form-urlencoded
- Body:
```
```

#### 参数说明
> 参数可指定ID（id）或编码（code）

#### Response
- Headers:  Content-Type: application/json
- Body
```
{
    "status": 200,
    "success": true,
    "message": null,
    "body": {
        "id": 5,
        "code": "RULESET_EXAMPLE",
        "name": "ruleset example",
        "remark": null,
        "defaultReturnValues": "{myvalue:['校验不通过','校验次数为0']}",
        "expression": "let rmap = seq.map('myvalue', '[\"校验不通过\",\"校验次数为0\"]');\nif((string.length(str(NAME)) >= 2 && string.length(str(NAME)) < 4) && str(MOBILE) =~ /^(138|139|189|199)\\d{8}$/){\nseq.put(rmap, 'myvalue', '[\"校验通过\",\"校验次数为1\"]');\n}\nreturn rmap;",
        "mode": 0
    }
}
```
</details>

<details>
  <summary><b>保存规则集</b></summary>

#### Request
- Method: **POST**
- URL:  ```/admin/rulesetInfo/save```
- Headers:  Content-Type: application/json
- Body:
```
{
  "code":"RULESET_EXAMPLE",
  "name":"ruleset example",
  "defaultReturnValues":"{'myvalue':'校验不通过'}"
}
```

#### Response
- Headers:  Content-Type: application/json
- Body
```
{
    "status": 200,
    "success": true,
    "message": null,
    "body": {
        "id": 5,
        "code": "RULESET_EXAMPLE",
        "name": "ruleset example",
        "remark": null,
        "defaultReturnValues": "{'myvalue':'校验不通过'}",
        "expression": null,
        "mode": 0
    }
}
```
</details>

<details>
  <summary><b>删除规则集</b></summary>

#### Request
- Method: **POST**
- URL:  ```/admin/rulesetInfo/delete```
- Headers:  Content-Type: application/json
- Body:
```
{
  "id":5
}
```

#### Response
- Headers:  Content-Type: application/json
- Body
```
{
    "status": 200,
    "success": true,
    "message": null,
    "body": null
}
```
</details>

<details>
  <summary><b>查询规则（分页）（指定规则集）</b></summary>

#### Request
- Method: **GET**
- URL:  ```/admin/ruleInfo/query?page=0&size=10&rulesetId=5```
- Headers:  Content-Type: application/x-www-form-urlencoded
- Body:
```
```

#### 参数说明
> 规则集可指定ID（rulesetId）或编码（rulesetCode）

#### Response
- Headers:  Content-Type: application/json
- Body
```
{
    "status": 200,
    "success": true,
    "message": null,
    "body": {
        "content": [
            {
                "id": 17,
                "rulesetId": 5,
                "name": "Rule Example",
                "remark": null,
                "returnValues": "{'myvalue':'校验通过'}",
                "logicType": "AND",
                "priority": 99
            }
        ],
        "pageable": {
            "sort": {
                "sorted": true,
                "unsorted": false,
                "empty": false
            },
            "offset": 0,
            "pageSize": 10,
            "pageNumber": 0,
            "paged": true,
            "unpaged": false
        },
        "totalElements": 1,
        "last": true,
        "totalPages": 1,
        "size": 10,
        "number": 0,
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "numberOfElements": 1,
        "first": true,
        "empty": false
    }
}
```
</details>

<details>
  <summary><b>保存规则（指定规则集）</b></summary>

#### Request
- Method: **POST**
- URL:  ```/admin/ruleInfo/save```
- Headers:  Content-Type: application/json
- Body:
```
{
  "rulesetId":5,
  "name":"Rule Example",
  "returnValues":"{'myvalue':'校验通过'}",
  "logicType":"AND",
  "priority":"99"
}
```

#### 参数说明
> 规则集可指定ID（rulesetId）或编码（rulesetCode）

#### Response
- Headers:  Content-Type: application/json
- Body
```
{
    "status": 200,
    "success": true,
    "message": null,
    "body": {
        "id": 17,
        "rulesetId": 5,
        "name": "Rule Example",
        "remark": null,
        "returnValues": "{'myvalue':'校验通过'}",
        "logicType": "AND",
        "priority": 99
    }
}
```
</details>

<details>
  <summary><b>删除规则</b></summary>

#### Request
- Method: **POST**
- URL:  ```/admin/ruleInfo/delete```
- Headers:  Content-Type: application/json
- Body:
```
{
  "ruleId":17
}
```

#### Response
- Headers:  Content-Type: application/json
- Body
```
{
    "status": 200,
    "success": true,
    "message": null,
    "body": null
}
```
</details>

<details>
  <summary><b>查询条件列表（不分页）（指定规则）</b></summary>

#### Request
- Method: **GET**
- URL:  ```/admin/conditionInfoList/query?ruleId=17```
- Headers:  Content-Type: application/x-www-form-urlencoded
- Body:
```
```

#### Response
- Headers:  Content-Type: application/json
- Body
```
{
    "status": 200,
    "success": true,
    "message": null,
    "body": [
        {
            "id": 55,
            "ruleId": 17,
            "name": "条件-姓名",
            "remark": null,
            "variableName": "NAME",
            "referenceValue": "[2,4)",
            "relationType": "INTERVAL_STRING_LENGTH",
            "logicType": "AND",
            "priority": 100
        },
        {
            "id": 56,
            "ruleId": 17,
            "name": "条件-手机号码",
            "remark": null,
            "variableName": "MOBILE",
            "referenceValue": "/^(138|139|189|199)\\d{8}$/",
            "relationType": "REGEX",
            "logicType": "AND",
            "priority": 99
        }
    ]
}
```
</details>

<details>
  <summary><b>保存条件列表（全量更新）（指定规则）</b></summary>

#### Request
- Method: **POST**
- URL:  ```/admin/conditionInfoList/save```
- Headers:  Content-Type: application/json
- Body:
```
[
  {
    "ruleId": 17,
    "name": "条件-年龄",
    "variableName": "AGE",
    "referenceValue": 18,
    "relationType": "GREATER",
    "logicType": "AND",
    "priority": 100
  },
  {
    "ruleId": 17,
    "name": "条件-手机号码",
    "variableName": "MOBILE",
    "referenceValue": "/^(138|139|189|199)\\d{8}$/",
    "relationType": "REGEX",
    "logicType": "AND",
    "priority": 99
  }
]
```

#### Response
- Headers:  Content-Type: application/json
- Body
```
{
    "status": 200,
    "success": true,
    "message": null,
    "body": [
        {
            "id": 51,
            "ruleId": 17,
            "name": "条件-年龄",
            "remark": null,
            "variableName": "AGE",
            "referenceValue": "18",
            "relationType": "GREATER",
            "logicType": "AND",
            "priority": 100
        },
        {
            "id": 52,
            "ruleId": 17,
            "name": "条件-手机号码",
            "remark": null,
            "variableName": "MOBILE",
            "referenceValue": "/^(138|139|189|199)\\d{8}$/",
            "relationType": "REGEX",
            "logicType": "AND",
            "priority": 99
        }
    ]
}
```
</details>


