package com.github.instaer.ruleengine.expression;

import com.github.instaer.ruleengine.condition.Condition;
import com.github.instaer.ruleengine.condition.ConditionInstance;
import com.github.instaer.ruleengine.constants.ConditionLogicType;
import com.github.instaer.ruleengine.constants.ConditionRelationType;
import com.github.instaer.ruleengine.constants.RuleLogicType;
import com.github.instaer.ruleengine.exception.RuleRunTimeException;
import com.github.instaer.ruleengine.rule.entity.ConditionInfoEntity;
import com.github.instaer.ruleengine.rule.entity.RuleInfoEntity;
import com.github.instaer.ruleengine.rule.entity.RulesetInfoEntity;
import com.github.instaer.ruleengine.rule.repository.ConditionInfoRepository;
import com.github.instaer.ruleengine.rule.repository.RuleInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ExpressionBuildService {

    @Autowired
    private List<Condition> conditionList;

    @Autowired
    private RuleInfoRepository ruleInfoRepository;

    @Autowired
    private ConditionInfoRepository conditionInfoRepository;

    private Condition selectCondition(ConditionRelationType relationType) {
        if (CollectionUtils.isEmpty(conditionList)) {
            throw new RuleRunTimeException("No bean of type Condition was found");
        }

        return conditionList.stream()
                .filter(b -> Arrays.asList(b.relationTypes()).contains(relationType)).findFirst()
                .orElseThrow(() -> new RuleRunTimeException("No bean of type Condition found for " + relationType.name()));
    }

    /**
     * build ruleset expressions by combining multiple rules
     *
     * @param rulesetInfoEntity
     * @return
     */
    public String buildRulesetExpression(RulesetInfoEntity rulesetInfoEntity) {
        Long rulesetId = rulesetInfoEntity.getId();
        if (null == rulesetId || rulesetId <= 0) {
            throw new RuleRunTimeException("invalid parameter(rulesetId)");
        }

        List<RuleInfoEntity> ruleInfos = ruleInfoRepository.findByRulesetId(rulesetId);
        if (CollectionUtils.isEmpty(ruleInfos)) {
            throw new RuleRunTimeException("no ruleinfo under the ruleset");
        }

        if (ruleInfos.stream().map(RuleInfoEntity::getReturnValues).anyMatch(StringUtils::isEmpty)) {
            throw new RuleRunTimeException("all rules in the ruleset must set the return value");
        }

        StringBuilder rulesetExpression = new StringBuilder("let rmap = seq.map(");

        // set default return values collection for rule set
        String defaultReturnValues = rulesetInfoEntity.getDefaultReturnValues();
        if (StringUtils.isNotBlank(defaultReturnValues)) {
            String initRMapValue = Arrays.stream(defaultReturnValues.split(",")).map(v -> v.split(":"))
                    .map(a -> "'" + a[0] + "', " + stringValueEscape(a[1])).collect(Collectors.joining(", "));
            rulesetExpression.append(initRMapValue);
        }
        rulesetExpression.append(");\n");

        // sort by priority in desc order
        ruleInfos.sort(Comparator.comparingInt(RuleInfoEntity::getPriority).reversed());

        Iterator<RuleInfoEntity> ruleInfoIterator = ruleInfos.iterator();
        RuleLogicType lastRuleLogicType = null;
        while (ruleInfoIterator.hasNext()) {
            RuleInfoEntity ruleInfo = ruleInfoIterator.next();
            List<ConditionInfoEntity> conditionInfos = conditionInfoRepository.findByRuleId(ruleInfo.getId());
            if (CollectionUtils.isEmpty(conditionInfos)) {
                log.warn("no conditions found under the rule({}).", ruleInfo.getId());
                continue;
            }

            RuleLogicType currentRuleLogicType = RuleLogicType.getRuleLogicType(ruleInfo.getLogicType());
            String ruleExpression = buildRuleExpression(conditionInfos);

            // first rule
            if (null == lastRuleLogicType) {
                rulesetExpression.append("if(");
                if (ruleInfoIterator.hasNext() && RuleLogicType.AND.equals(currentRuleLogicType)) {
                    rulesetExpression.append("(")
                            .append(ruleExpression)
                            .append(") ")
                            .append(currentRuleLogicType.getValue())
                            .append(" ");
                }
                else if (!ruleInfoIterator.hasNext() || RuleLogicType.XOR.equals(currentRuleLogicType)) {
                    rulesetExpression.append(ruleExpression).append("){\n");
                }
            }
            else {
                if (RuleLogicType.AND.equals(lastRuleLogicType)) {
                    rulesetExpression.append("(").append(ruleExpression).append(")");
                    if (ruleInfoIterator.hasNext() && RuleLogicType.AND.equals(currentRuleLogicType)) {
                        rulesetExpression.append(" ").append(currentRuleLogicType.getValue()).append(" ");
                    }
                    else if (!ruleInfoIterator.hasNext() || RuleLogicType.XOR.equals(currentRuleLogicType)) {
                        rulesetExpression.append("){\n");
                    }
                }
                else if (RuleLogicType.XOR.equals(lastRuleLogicType)) {
                    rulesetExpression.append("elsif(");
                    if (ruleInfoIterator.hasNext() && RuleLogicType.AND.equals(currentRuleLogicType)) {
                        rulesetExpression.append("(")
                                .append(ruleExpression)
                                .append(") ")
                                .append(currentRuleLogicType.getValue())
                                .append(" ");
                    }
                    else if (!ruleInfoIterator.hasNext() || RuleLogicType.XOR.equals(currentRuleLogicType)) {
                        rulesetExpression.append(ruleExpression).append("){\n");
                    }
                }
            }

            if (!ruleInfoIterator.hasNext() || RuleLogicType.XOR.equals(currentRuleLogicType)) {
                Arrays.stream(ruleInfo.getReturnValues().split(",")).map(v -> v.split(":"))
                        .forEach(a -> rulesetExpression.append("seq.put(rmap, '")
                                .append(a[0]).append("', ")
                                .append(stringValueEscape(a[1]))
                                .append(");\n"));
                rulesetExpression.append("}\n");
            }

            lastRuleLogicType = currentRuleLogicType;
        }

        rulesetExpression.append("return rmap;");
        return rulesetExpression.toString();
    }

    /**
     * build rule expressions by combining multiple conditions
     *
     * @param conditionInfos condition list under a single rule
     * @return The return value only represents the combination of multiple conditions under a single rule,
     * and does not include the return value
     */
    private String buildRuleExpression(List<ConditionInfoEntity> conditionInfos) {
        if (CollectionUtils.isEmpty(conditionInfos)) {
            throw new RuleRunTimeException("condition list is empty");
        }

        List<ConditionInstance> conditionInstances = new ArrayList<>();
        for (ConditionInfoEntity conditionInfo : conditionInfos) {
            ConditionRelationType relationType = Optional.ofNullable(ConditionRelationType.getConditionRelationType(conditionInfo.getRelationType()))
                    .orElseThrow(() -> new RuleRunTimeException("invalid parameter(relationType):" + conditionInfo.getRelationType()));
            ConditionLogicType logicType = Optional.ofNullable(ConditionLogicType.getConditionLogicType(conditionInfo.getLogicType()))
                    .orElseThrow(() -> new RuleRunTimeException("invalid parameter(logicType):" + conditionInfo.getLogicType()));

            // When the condition relation type is regex, not convert it to a string by adding single quotes.
            String referenceValue = conditionInfo.getReferenceValue();
            if (!ConditionRelationType.REGEX.equals(relationType)) {
                referenceValue = stringValueEscape(conditionInfo.getReferenceValue());
            }

            ConditionInstance conditionInstance = ConditionInstance.builder()
                    .variableName(conditionInfo.getVariableName())
                    .relationType(relationType)
                    .referenceValue(referenceValue)
                    .priority(conditionInfo.getPriority())
                    .logicType(logicType)
                    .build();
            conditionInstances.add(conditionInstance);
        }

        log.info("## build condition expression start");

        // sort by priority in desc order
        conditionInstances.sort(Comparator.comparingInt(ConditionInstance::getPriority).reversed());

        StringBuilder finalExpression = new StringBuilder();
        for (int i = 0, conditionInstancesSize = conditionInstances.size(); i < conditionInstancesSize; i++) {
            ConditionInstance conditionInstance = conditionInstances.get(i);
            Condition condition = selectCondition(conditionInstance.getRelationType());
            finalExpression.append(condition.build(conditionInstance));

            if (i < conditionInstancesSize - 1) {
                if (i > 0) {
                    // Add parentheses to conditions to ensure priority
                    finalExpression.insert(0, "(");
                    finalExpression.append(")");
                }

                // Ignore the conditional logic operator with the lowest priority
                finalExpression.append(" ").append(conditionInstance.getLogicType().getValue()).append(" ");
            }
        }

        log.info("## build condition expression success => {}", finalExpression);
        return finalExpression.toString();
    }

    /**
     * wrap non-boolean and non-numeric string value with single quote
     *
     * @param value
     * @return
     */
    private static String stringValueEscape(String value) {
        if (Boolean.TRUE.toString().equals(value) || Boolean.FALSE.toString().equals(value) ||
                NumberUtils.isCreatable(value)) {
            return value;
        }

        return "'" + value.replace("'", "\\'") + "'";
    }
}