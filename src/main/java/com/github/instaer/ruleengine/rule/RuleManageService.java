package com.github.instaer.ruleengine.rule;

import com.github.instaer.ruleengine.common.dto.ConditionInfoDTO;
import com.github.instaer.ruleengine.common.dto.RuleInfoDTO;
import com.github.instaer.ruleengine.common.dto.RulesetInfoDTO;
import com.github.instaer.ruleengine.common.entity.ConditionInfoEntity;
import com.github.instaer.ruleengine.common.entity.RuleInfoEntity;
import com.github.instaer.ruleengine.common.entity.RulesetInfoEntity;
import com.github.instaer.ruleengine.common.mapper.EntityMapper;
import com.github.instaer.ruleengine.common.vo.ConditionInfoVO;
import com.github.instaer.ruleengine.common.vo.RuleInfoVO;
import com.github.instaer.ruleengine.common.vo.RulesetInfoVO;
import com.github.instaer.ruleengine.constants.RulesetMode;
import com.github.instaer.ruleengine.exception.RuleRunTimeException;
import com.github.instaer.ruleengine.expression.ExpressionBuildService;
import com.github.instaer.ruleengine.repository.ConditionInfoRepository;
import com.github.instaer.ruleengine.repository.RuleInfoRepository;
import com.github.instaer.ruleengine.repository.RulesetInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
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

    public void refreshRulesetInfo(RulesetInfoDTO rulesetInfoDTO) {
        Long rulesetId = rulesetInfoDTO.getId();
        if (null == rulesetId || rulesetId <= 0) {
            throw new RuleRunTimeException("invalid parameter(rulesetId)");
        }
        refreshRulesetInfo(rulesetId);
    }

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

    public Page<RulesetInfoVO> queryRulesetInfoPage(RulesetInfoDTO rulesetInfoDTO) {
        RulesetInfoEntity rulesetInfoEntity = new RulesetInfoEntity();
        rulesetInfoEntity.setCode(rulesetInfoDTO.getCode());
        Example<RulesetInfoEntity> example = Example.of(rulesetInfoEntity);

        // pageNumber start from 0
        Pageable pageable = PageRequest.of(rulesetInfoDTO.getPage(), rulesetInfoDTO.getSize(), Sort.Direction.DESC, "id");
        return rulesetInfoRepository.findAll(example, pageable).map(EntityMapper.INSTANCE::toVO);
    }

    public RulesetInfoVO queryRulesetInfoDetail(RulesetInfoDTO rulesetInfoDTO) {
        if (null == rulesetInfoDTO.getId() && StringUtils.isBlank(rulesetInfoDTO.getCode())) {
            throw new RuleRunTimeException("invalid parameter(id or code)");
        }

        RulesetInfoEntity rulesetInfoEntity = new RulesetInfoEntity();
        rulesetInfoEntity.setId(rulesetInfoDTO.getId());
        rulesetInfoEntity.setCode(rulesetInfoDTO.getCode());
        Example<RulesetInfoEntity> example = Example.of(rulesetInfoEntity);

        return rulesetInfoRepository.findOne(example).map(EntityMapper.INSTANCE::toVO)
                .orElseThrow(() -> new RuleRunTimeException("invalid parameter(id or code)"));
    }

    public RulesetInfoVO saveRulesetInfo(RulesetInfoDTO rulesetInfoDTO) {
        RulesetInfoEntity rulesetInfoEntity = EntityMapper.INSTANCE.toEntity(rulesetInfoDTO);
        RulesetInfoEntity findRulesetInfoEntity = rulesetInfoRepository.findByCode(rulesetInfoEntity.getCode());
        if (null != findRulesetInfoEntity && !findRulesetInfoEntity.getId().equals(rulesetInfoEntity.getId())) {
            throw new RuleRunTimeException("ruleset code already exists");
        }
        if (StringUtils.isBlank(rulesetInfoEntity.getName())) {
            throw new RuleRunTimeException("invalid parameter(name)");
        }

        Long rulesetId = rulesetInfoEntity.getId();
        if (null == rulesetId) {
            if (StringUtils.isBlank(rulesetInfoEntity.getCode())) {
                throw new RuleRunTimeException("invalid parameter(code)");
            }
            rulesetInfoEntity.setMode(RulesetMode.BUILDING.getCode());
        }
        else {
            RulesetInfoEntity oldRulesetInfoEntity = rulesetInfoRepository.findById(rulesetId)
                    .orElseThrow(() -> new RuleRunTimeException("invalid parameter(id)"));
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

        return EntityMapper.INSTANCE.toVO(rulesetInfoRepository.save(rulesetInfoEntity));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRulesetInfo(RulesetInfoDTO rulesetInfoDTO) {
        Long rulesetId = rulesetInfoDTO.getId();
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

    public Page<RuleInfoVO> queryRuleInfoPage(RuleInfoDTO ruleInfoDTO) {
        Long rulesetId;
        String rulesetCode = ruleInfoDTO.getRulesetCode();
        if (StringUtils.isBlank(rulesetCode)) {
            rulesetId = ruleInfoDTO.getRulesetId();
            if (null == rulesetId || rulesetId <= 0) {
                return new PageImpl<>(Collections.emptyList());
            }
        }
        else {
            RulesetInfoEntity rulesetInfoEntity = rulesetInfoRepository.findByCode(rulesetCode);
            if (null == rulesetInfoEntity) {
                throw new RuleRunTimeException("invalid parameter(rulesetCode)");
            }
            rulesetId = rulesetInfoEntity.getId();
        }

        Pageable pageable = PageRequest.of(ruleInfoDTO.getPage(), ruleInfoDTO.getSize(), Sort.Direction.DESC, "priority", "id");
        return ruleInfoRepository.findByRulesetId(rulesetId, pageable).map(EntityMapper.INSTANCE::toVO);
    }

    @Transactional(rollbackFor = Exception.class)
    public RuleInfoVO saveRuleInfo(RuleInfoDTO ruleInfoDTO) {
        RuleInfoEntity ruleInfoEntity = EntityMapper.INSTANCE.toEntity(ruleInfoDTO);
        Long rulesetId = ruleInfoEntity.getRulesetId();
        if (null == rulesetId || rulesetId <= 0) {
            throw new RuleRunTimeException("invalid parameter(rulesetId)");
        }
        if (StringUtils.isAnyBlank(ruleInfoEntity.getName(), ruleInfoEntity.getReturnValues(), ruleInfoEntity.getLogicType())) {
            throw new RuleRunTimeException("invalid parameter(name, returnValues, logicType)");
        }
        if (null == ruleInfoEntity.getPriority()) {
            throw new RuleRunTimeException("invalid parameter(priority)");
        }

        boolean refreshRulesetFlag = false;
        Long ruleId = ruleInfoEntity.getId();
        if (null == ruleId) {
            if (null == ruleInfoEntity.getRulesetId()) {
                throw new RuleRunTimeException("invalid parameter(rulesetId)");
            }
        }
        else {
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
        return EntityMapper.INSTANCE.toVO(ruleInfoEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRuleInfo(RuleInfoDTO ruleInfoDTO) {
        Long ruleId = ruleInfoDTO.getId();
        if (null == ruleId || ruleId <= 0) {
            throw new RuleRunTimeException("invalid parameter(ruleId)");
        }

        conditionInfoRepository.deleteByRuleId(ruleId);
        ruleInfoRepository.deleteById(ruleId);

        RuleInfoEntity ruleInfoEntity = ruleInfoRepository.findById(ruleId)
                .orElseThrow(() -> new RuleRunTimeException("invalid parameter(ruleId)"));
        refreshRulesetInfo(ruleInfoEntity.getRulesetId());
    }

    public List<ConditionInfoVO> queryConditionInfoList(ConditionInfoDTO conditionInfoDTO) {
        Long ruleId = conditionInfoDTO.getRuleId();
        if (null == ruleId || ruleId <= 0) {
            throw new RuleRunTimeException("invalid parameter(ruleId)");
        }

        return EntityMapper.INSTANCE.toVOList(conditionInfoRepository.findByRuleId(ruleId));
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ConditionInfoVO> saveConditionInfoList(List<ConditionInfoDTO> conditionInfoDTOList) {
        List<ConditionInfoEntity> conditionInfoEntityList = EntityMapper.INSTANCE.toEntityList(conditionInfoDTOList);
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
        return EntityMapper.INSTANCE.toVOList(conditionInfoEntityList);
    }
}