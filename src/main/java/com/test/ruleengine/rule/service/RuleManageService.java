package com.test.ruleengine.rule.service;

import com.test.ruleengine.condition.ConditionInstance;
import com.test.ruleengine.constants.ConditionLogicType;
import com.test.ruleengine.constants.ConditionOperateType;
import com.test.ruleengine.constants.RuleMode;
import com.test.ruleengine.exception.RuleRunTimeException;
import com.test.ruleengine.expression.ExpressionBuildService;
import com.test.ruleengine.rule.entity.ConditionInfoEntity;
import com.test.ruleengine.rule.entity.RuleInfoEntity;
import com.test.ruleengine.rule.repository.ConditionInfoRepository;
import com.test.ruleengine.rule.repository.RuleInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RuleManageService {

    @Autowired
    private RuleInfoRepository ruleInfoRepository;

    @Autowired
    private ConditionInfoRepository conditionInfoRepository;

    @Autowired
    private ExpressionBuildService expressionBuildService;

    public Page<RuleInfoEntity> findRuleInfoPage(Integer page, Integer size) {
        // pageNumber start from 0
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        return ruleInfoRepository.findAll(pageable);
    }

    public Page<ConditionInfoEntity> findConditionInfoPage(Long ruleId, Integer page, Integer size) {
        if (null == ruleId || ruleId <= 0) {
            throw new RuleRunTimeException("invalid ruleId:" + ruleId);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "priority", "id");
        return conditionInfoRepository.findByRuleId(ruleId, pageable);
    }

    public RuleInfoEntity saveRuleInfo(RuleInfoEntity ruleInfoEntity) {
        RuleInfoEntity findRuleInfoEntity = ruleInfoRepository.findByCode(ruleInfoEntity.getCode());
        if (null != findRuleInfoEntity && !findRuleInfoEntity.getId().equals(ruleInfoEntity.getId())) {
            throw new RuleRunTimeException("rule code (" + ruleInfoEntity.getCode() + ") already exists");
        }

        return ruleInfoRepository.save(ruleInfoEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRuleInfo(Long ruleId) {
        if (null == ruleId || ruleId <= 0) {
            throw new RuleRunTimeException("invalid ruleId:" + ruleId);
        }

        conditionInfoRepository.deleteByRuleId(ruleId);
        ruleInfoRepository.deleteById(ruleId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveConditionInfoList(List<ConditionInfoEntity> conditionInfoEntityList) {
        if (CollectionUtils.isEmpty(conditionInfoEntityList)) {
            throw new RuleRunTimeException("empty conditionInfoEntityList");
        }

        boolean hasSamePriority = conditionInfoEntityList.stream().map(ConditionInfoEntity::getPriority).distinct()
                .count() < conditionInfoEntityList.size();
        if (hasSamePriority) {
            throw new RuleRunTimeException("cannot contain conditions of the same priority");
        }

        // remove existing conditionInfo
        Long ruleId = conditionInfoEntityList.get(0).getRuleId();
        conditionInfoRepository.deleteByRuleId(ruleId);

        conditionInfoEntityList = conditionInfoRepository.saveAll(conditionInfoEntityList);

        // rebuild new rule expression
        refreshRuleExpression(conditionInfoEntityList);
    }

    public void refreshRuleExpression(Long ruleId) {
        if (null == ruleId || ruleId <= 0) {
            throw new RuleRunTimeException("invalid ruleId:" + ruleId);
        }

        List<ConditionInfoEntity> conditionInfoEntityList = conditionInfoRepository.findByRuleId(ruleId);
        if (CollectionUtils.isEmpty(conditionInfoEntityList)) {
            RuleInfoEntity ruleInfoEntity = ruleInfoRepository.findById(ruleId)
                    .orElseThrow(() -> new RuleRunTimeException("invalid ruleId:" + ruleId));
            ruleInfoEntity.setMode(RuleMode.RULE_SET.getCode());
            ruleInfoEntity.setExpression(null);
            ruleInfoRepository.save(ruleInfoEntity);
            return;
        }

        refreshRuleExpression(conditionInfoEntityList);
    }

    private void refreshRuleExpression(List<ConditionInfoEntity> conditionInfoEntityList) {
        List<ConditionInstance> conditionInstances = new ArrayList<>();
        conditionInfoEntityList.forEach(condition -> {
            ConditionOperateType operateType = Optional.ofNullable(ConditionOperateType.getConditionOperateType(condition.getOperateType()))
                    .orElseThrow(() -> new RuleRunTimeException("invalid operateType:" + condition.getOperateType()));
            ConditionLogicType logicType = Optional.ofNullable(ConditionLogicType.getConditionLogicType(condition.getLogicType()))
                    .orElseThrow(() -> new RuleRunTimeException("invalid logicType:" + condition.getLogicType()));

            ConditionInstance conditionInstance = ConditionInstance.builder()
                    .variableName(condition.getVariableName())
                    .operateType(operateType)
                    .referenceValue(condition.getReferenceValue())
                    .priority(condition.getPriority())
                    .logicType(logicType)
                    .build();
            conditionInstances.add(conditionInstance);
        });
        String ruleExpression = expressionBuildService.buildExpression(conditionInstances);

        // update rule expression
        long ruleId = conditionInfoEntityList.get(0).getRuleId();
        RuleInfoEntity ruleInfoEntity = ruleInfoRepository.findById(ruleId)
                .orElseThrow(() -> new RuleRunTimeException("invalid rule id:" + ruleId));
        ruleInfoEntity.setExpression(ruleExpression);
        ruleInfoEntity.setMode(RuleMode.EXPRESSION_SET.getCode());
        ruleInfoRepository.save(ruleInfoEntity);
    }
}