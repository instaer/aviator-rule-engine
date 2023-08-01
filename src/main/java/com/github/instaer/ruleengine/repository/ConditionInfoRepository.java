package com.github.instaer.ruleengine.repository;

import com.github.instaer.ruleengine.common.entity.ConditionInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConditionInfoRepository extends JpaRepository<ConditionInfoEntity, Long> {

    List<ConditionInfoEntity> findByRuleId(Long ruleId);

    void deleteByRuleId(Long ruleId);
}