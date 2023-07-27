package com.github.instaer.ruleengine.rule.service;

import com.github.instaer.ruleengine.common.RulesetInfoDTO;
import com.github.instaer.ruleengine.constants.RulesetMode;
import com.github.instaer.ruleengine.exception.RuleRunTimeException;
import com.github.instaer.ruleengine.expression.ExpressionBuildService;
import com.github.instaer.ruleengine.rule.entity.ConditionInfoEntity;
import com.github.instaer.ruleengine.rule.entity.RuleInfoEntity;
import com.github.instaer.ruleengine.rule.entity.RulesetInfoEntity;
import com.github.instaer.ruleengine.rule.repository.ConditionInfoRepository;
import com.github.instaer.ruleengine.rule.repository.RuleInfoRepository;
import com.github.instaer.ruleengine.rule.repository.RulesetInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Component
public class RuleManageService {

    @Autowired
    private RulesetInfoRepository rulesetInfoRepository;

    @Autowired
    private RuleInfoRepository ruleInfoRepository;

    @Autowired
    private ConditionInfoRepository conditionInfoRepository;

    @Autowired
    private ExpressionBuildService expressionBuildService;

    /**
     * refresh the expression and mode of a ruleset
     *
     * @param rulesetId
     */
    public void refreshRulesetInfo(Long rulesetId) {
        RulesetInfoEntity rulesetInfoEntity = rulesetInfoRepository.findById(rulesetId)
                .orElseThrow(() -> new RuleRunTimeException("invalid parameter(rulesetId)"));
        String rulesetExpression = expressionBuildService.buildRulesetExpression(rulesetInfoEntity);
        if (StringUtils.isNotEmpty(rulesetExpression)) {
            rulesetInfoEntity.setMode(RulesetMode.BUILT.getCode());
            rulesetInfoEntity.setExpression(rulesetExpression);
            rulesetInfoRepository.save(rulesetInfoEntity);
            log.info("ruleset({}) refresh completed", rulesetId);
        }
    }

    public Page<RulesetInfoEntity> queryRulesetInfo(RulesetInfoDTO dto) {
        RulesetInfoEntity rulesetInfoEntity = new RulesetInfoEntity();
        rulesetInfoEntity.setCode(dto.getRulesetCode());
        Example<RulesetInfoEntity> example = Example.of(rulesetInfoEntity);

        // pageNumber start from 0
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), Sort.Direction.DESC, "id");
        return rulesetInfoRepository.findAll(example, pageable);
    }

    public RulesetInfoEntity saveRulesetInfo(RulesetInfoEntity rulesetInfoEntity) {
        RulesetInfoEntity findRulesetInfoEntity = rulesetInfoRepository.findByCode(rulesetInfoEntity.getCode());
        if (null != findRulesetInfoEntity && !findRulesetInfoEntity.getId().equals(rulesetInfoEntity.getId())) {
            throw new RuleRunTimeException("ruleset code already exists");
        }

        Long rulesetId = rulesetInfoEntity.getId();
        if (null == rulesetId) {
            rulesetInfoEntity.setMode(RulesetMode.BUILDING.getCode());
        }
        else {
            RulesetInfoEntity oldRulesetInfoEntity = rulesetInfoRepository.findById(rulesetId)
                    .orElseThrow(() -> new RuntimeException("invalid parameter(id)"));
            rulesetInfoEntity.setCode(oldRulesetInfoEntity.getCode());
            rulesetInfoEntity.setExpression(oldRulesetInfoEntity.getExpression());
            rulesetInfoEntity.setMode(oldRulesetInfoEntity.getMode());

            // rebuild ruleset expression when defaultReturnValues changes
            String oldDefaultReturnValues = oldRulesetInfoEntity.getDefaultReturnValues();
            if (null != oldDefaultReturnValues && !oldDefaultReturnValues.equals(rulesetInfoEntity.getDefaultReturnValues())) {
                String newRulesetExpression = expressionBuildService.buildRulesetExpression(rulesetInfoEntity);
                if (StringUtils.isNotEmpty(newRulesetExpression)) {
                    rulesetInfoEntity.setMode(RulesetMode.BUILT.getCode());
                    rulesetInfoEntity.setExpression(newRulesetExpression);
                }
            }
        }

        return rulesetInfoRepository.save(rulesetInfoEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRulesetInfo(Long rulesetId) {
        if (null == rulesetId || rulesetId <= 0) {
            throw new RuleRunTimeException("invalid parameter(rulesetId)");
        }

        List<RuleInfoEntity> findRuleInfoEntityList = ruleInfoRepository.findByRulesetId(rulesetId);
        if (!CollectionUtils.isEmpty(findRuleInfoEntityList)) {
            for (RuleInfoEntity ruleInfoEntity : findRuleInfoEntityList) {
                conditionInfoRepository.deleteByRuleId(ruleInfoEntity.getId());
                ruleInfoRepository.deleteById(ruleInfoEntity.getId());
            }
        }

        rulesetInfoRepository.deleteById(rulesetId);
    }

    public Page<RuleInfoEntity> queryRuleInfo(Long rulesetId, Integer page, Integer size) {
        if (null == rulesetId || rulesetId <= 0) {
            throw new RuleRunTimeException("invalid parameter(rulesetId)");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "priority", "id");
        return ruleInfoRepository.findByRulesetId(rulesetId, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public RuleInfoEntity saveRuleInfo(RuleInfoEntity ruleInfoEntity) {
        Long rulesetId = ruleInfoEntity.getRulesetId();
        if (null == rulesetId || rulesetId <= 0) {
            throw new RuleRunTimeException("invalid parameter(rulesetId)");
        }

        boolean refreshRulesetFlag = false;
        Long ruleId = ruleInfoEntity.getId();
        if (null != ruleId) {
            RuleInfoEntity oldRuleInfoEntity = ruleInfoRepository.findById(ruleId)
                    .orElseThrow(() -> new RuleRunTimeException("invalid parameter(id)"));
            ruleInfoEntity.setRulesetId(oldRuleInfoEntity.getRulesetId());

            // rebuild ruleset expression when returnValue or logicType changes
            String oldReturnValues = oldRuleInfoEntity.getReturnValues();
            String oldLogicType = oldRuleInfoEntity.getLogicType();
            Integer oldPriority = oldRuleInfoEntity.getPriority();
            refreshRulesetFlag = (null != oldReturnValues && !oldReturnValues.equals(ruleInfoEntity.getReturnValues())) ||
                    (null != oldLogicType && !oldLogicType.equals(ruleInfoEntity.getLogicType())) ||
                    (null != oldPriority && !oldPriority.equals(ruleInfoEntity.getPriority()));
        }

        ruleInfoRepository.save(ruleInfoEntity);
        if (refreshRulesetFlag) {
            refreshRulesetInfo(rulesetId);
        }
        return ruleInfoEntity;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRuleInfo(Long ruleId) {
        if (null == ruleId || ruleId <= 0) {
            throw new RuleRunTimeException("invalid parameter(ruleId)");
        }

        conditionInfoRepository.deleteByRuleId(ruleId);
        ruleInfoRepository.deleteById(ruleId);

        RuleInfoEntity ruleInfoEntity = ruleInfoRepository.findById(ruleId)
                .orElseThrow(() -> new RuleRunTimeException("invalid parameter(ruleId)"));
        refreshRulesetInfo(ruleInfoEntity.getRulesetId());
    }

    public Page<ConditionInfoEntity> queryConditionInfo(Long ruleId, Integer page, Integer size) {
        if (null == ruleId || ruleId <= 0) {
            throw new RuleRunTimeException("invalid parameter(ruleId)");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "priority", "id");
        return conditionInfoRepository.findByRuleId(ruleId, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ConditionInfoEntity> saveConditionInfoList(List<ConditionInfoEntity> conditionInfoEntityList) {
        if (CollectionUtils.isEmpty(conditionInfoEntityList)) {
            throw new RuleRunTimeException("condition info list is empty");
        }

        boolean hasSamePriority = conditionInfoEntityList.stream().map(ConditionInfoEntity::getPriority).distinct()
                .count() < conditionInfoEntityList.size();
        if (hasSamePriority) {
            throw new RuleRunTimeException("conditions under a rule cannot be set with the same priority");
        }

        // remove existing conditionInfo and save the new
        Long ruleId = conditionInfoEntityList.get(0).getRuleId();
        conditionInfoRepository.deleteByRuleId(ruleId);
        conditionInfoEntityList = conditionInfoRepository.saveAll(conditionInfoEntityList);

        RuleInfoEntity ruleInfoEntity = ruleInfoRepository.findById(ruleId)
                .orElseThrow(() -> new RuleRunTimeException("invalid parameter(ruleId)"));
        refreshRulesetInfo(ruleInfoEntity.getRulesetId());
        return conditionInfoEntityList;
    }
}