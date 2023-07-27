package com.github.instaer.ruleengine.rule.repository;

import com.github.instaer.ruleengine.rule.entity.RulesetInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RulesetInfoRepository extends JpaRepository<RulesetInfoEntity, Long> {
    RulesetInfoEntity findByCode(String code);
}