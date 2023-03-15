package com.github.instaer.ruleengine.rule.repository;

import com.github.instaer.ruleengine.rule.entity.ConditionInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConditionInfoRepository extends JpaRepository<ConditionInfoEntity, Long> {

    List<ConditionInfoEntity> findByRuleId(Long ruleId);

    Page<ConditionInfoEntity> findByRuleId(Long ruleId, Pageable pageable);

    void deleteByRuleId(Long ruleId);
}