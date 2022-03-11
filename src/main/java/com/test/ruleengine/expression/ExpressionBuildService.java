package com.test.ruleengine.expression;

import com.test.ruleengine.condition.Condition;
import com.test.ruleengine.constants.ConditionOperateType;
import com.test.ruleengine.condition.ConditionInstance;
import com.test.ruleengine.exception.RuleRunTimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
@Component
public class ExpressionBuildService {

    @Autowired
    private List<Condition> conditionList;

    private Condition selectCondition(ConditionOperateType operateType) {
        if (CollectionUtils.isEmpty(conditionList)) {
            throw new RuleRunTimeException("no bean of type Condition was found");
        }

        return conditionList.stream()
                .filter(b -> Arrays.asList(b.operateTypes()).contains(operateType)).findFirst()
                .orElseThrow(() -> new RuleRunTimeException("no bean of type BaseCondition found for " + operateType.name()));
    }

    public String buildExpression(List<ConditionInstance> conditionInstances) {
        log.info("## build expression start");

        if (CollectionUtils.isEmpty(conditionInstances)) {
            throw new RuleRunTimeException("empty conditionInstances");
        }

        // sort by priority in descending order
        conditionInstances.sort(Comparator.comparingInt(ConditionInstance::getPriority).reversed());

        StringBuilder finalExpression = new StringBuilder();
        for (int i = 0, conditionInstancesSize = conditionInstances.size(); i < conditionInstancesSize; i++) {
            ConditionInstance conditionInstance = conditionInstances.get(i);
            Condition condition = selectCondition(conditionInstance.getOperateType());
            finalExpression.append(condition.buildCondition(conditionInstance));

            if (i < conditionInstancesSize - 1) {
                if (i > 0) {
                    // Add parentheses to conditions to ensure priority
                    finalExpression.insert(0, "(");
                    finalExpression.append(")");
                }

                // Ignore the conditional logical operator with the lowest priority
                finalExpression.append(" ").append(conditionInstance.getLogicType().getValue()).append(" ");
            }
        }

        log.info("## build expression success => {}", finalExpression);
        return finalExpression.toString();
    }
}