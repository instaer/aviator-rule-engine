package com.github.instaer.ruleengine.common.mapper;

import com.github.instaer.ruleengine.common.dto.ConditionInfoDTO;
import com.github.instaer.ruleengine.common.dto.RuleInfoDTO;
import com.github.instaer.ruleengine.common.dto.RulesetInfoDTO;
import com.github.instaer.ruleengine.rule.entity.ConditionInfoEntity;
import com.github.instaer.ruleengine.rule.entity.RuleInfoEntity;
import com.github.instaer.ruleengine.rule.entity.RulesetInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EntityMapper {
    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    RulesetInfoEntity toEntity(RulesetInfoDTO dto);

    RuleInfoEntity toEntity(RuleInfoDTO dto);

    ConditionInfoEntity toEntity(ConditionInfoDTO dto);

    List<ConditionInfoEntity> toEntityList(List<ConditionInfoDTO> dtoList);
}
