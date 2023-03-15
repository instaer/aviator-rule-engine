package com.github.instaer.ruleengine.rule.repository;

import com.github.instaer.ruleengine.rule.entity.RuleInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleInfoRepository extends JpaRepository<RuleInfoEntity, Long> {
    List<RuleInfoEntity> findByRulesetId(Long rulesetId);

    Page<RuleInfoEntity> findByRulesetId(Long rulesetId, Pageable pageable);
}