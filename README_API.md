# 接口文档
本项目中主要有两类接口：外部请求接口和内部管理接口。

## 外部请求接口

<details open>
  <summary><b>执行规则集</b></summary>

#### Request
- Method: **POST**
- URL:  ```/admin/executeRuleset```
- Headers:  Content-Type: application/json
- Body:
```
{
  "rulesetCode":"RULESET_EXAMPLE",
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
- URL:  ```/admin/conditionLogicTypeMap```
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
- URL:  ```/admin/conditionRelationTypeMap```
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
- URL:  ```/admin/ruleLogicTypeMap```
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
- URL:  ```/admin/refreshRuleset```
- Headers:  Content-Type: application/json
- Body:
```
{
  "rulesetId":5
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
- URL:  ```/admin/findRulesetInfoPage?page=0&size=10&rulesetCode=RULESET_EXAMPLE```
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
  <summary><b>保存规则集</b></summary>

#### Request
- Method: **POST**
- URL:  ```/admin/saveRulesetInfo```
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
- URL:  ```/admin/deleteRulesetInfo```
- Headers:  Content-Type: application/json
- Body:
```
{
  "rulesetId":5
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
  <summary><b>查询规则（分页）</b></summary>

#### Request
- Method: **GET**
- URL:  ```/admin/findRuleInfoPage?page=0&size=10&rulesetId=5```
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
  <summary><b>保存规则</b></summary>

#### Request
- Method: **POST**
- URL:  ```/admin/saveRuleInfo```
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
- URL:  ```/admin/deleteRuleInfo```
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
  <summary><b>查询条件（分页）</b></summary>

#### Request
- Method: **GET**
- URL:  ```/admin/findConditionInfoPage?page=0&size=10&ruleId=17```
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
        "totalElements": 2,
        "last": true,
        "totalPages": 1,
        "size": 10,
        "number": 0,
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "numberOfElements": 2,
        "first": true,
        "empty": false
    }
}
```
</details>

<details>
  <summary><b>保存条件</b></summary>

#### Request
- Method: **POST**
- URL:  ```/admin/saveConditionInfoList```
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

