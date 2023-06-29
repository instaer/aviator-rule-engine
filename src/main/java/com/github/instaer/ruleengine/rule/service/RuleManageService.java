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
import org.apache.commons.lang3.exception.ExceptionUtils;
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
    public void refreshRuleset(Long rulesetId) {
        RulesetInfoEntity rulesetInfoEntity = rulesetInfoRepository.findById(rulesetId)
                .orElseThrow(() -> new RuleRunTimeException("invalid parameter(rulesetId):" + rulesetId));

        try {
            String rulesetExpression = expressionBuildService.buildRulesetExpression(rulesetInfoEntity);
            if (StringUtils.isNotEmpty(rulesetExpression)) {
                rulesetInfoEntity.setMode(RulesetMode.BUILT.getCode());
                rulesetInfoEntity.setExpression(rulesetExpression);
                rulesetInfoRepository.save(rulesetInfoEntity);
                log.info("ruleset({}) refresh completed", rulesetId);
                return;
            }
        } catch (Exception e) {
            log.warn("ruleset({}) cannot be refreshed at this time. ({})", rulesetId, ExceptionUtils.getRootCauseMessage(e));
        }

        // reset the ruleset to its initial state
        rulesetInfoEntity.setMode(RulesetMode.BUILDING.getCode());
        rulesetInfoEntity.setExpression(null);
        rulesetInfoRepository.save(rulesetInfoEntity);
    }

    public Page<RulesetInfoEntity> findRulesetInfoPage(RulesetInfoDTO dto) {
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

    public Page<RuleInfoEntity> findRuleInfoPage(Long rulesetId, Integer page, Integer size) {
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
        ruleInfoEntity = ruleInfoRepository.save(ruleInfoEntity);

        // refresh ruleset
        refreshRuleset(rulesetId);

        return ruleInfoEntity;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRuleInfo(Long ruleId) {
        if (null == ruleId || ruleId <= 0) {
            throw new RuleRunTimeException("invalid parameter(ruleId)");
        }

        conditionInfoRepository.deleteByRuleId(ruleId);
        ruleInfoRepository.deleteById(ruleId);

        // refresh ruleset
        RuleInfoEntity ruleInfo = ruleInfoRepository.getOne(ruleId);
        refreshRuleset(ruleInfo.getRulesetId());
    }

    public Page<ConditionInfoEntity> findConditionInfoPage(Long ruleId, Integer page, Integer size) {
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

        // refresh ruleset
        RuleInfoEntity ruleInfo = ruleInfoRepository.getOne(ruleId);
        refreshRuleset(ruleInfo.getRulesetId());

        return conditionInfoEntityList;
    }
}