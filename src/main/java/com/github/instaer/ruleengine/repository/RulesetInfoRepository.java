package com.github.instaer.ruleengine.repository;

import com.github.instaer.ruleengine.common.entity.RulesetInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RulesetInfoRepository extends JpaRepository<RulesetInfoEntity, Long> {
    RulesetInfoEntity findByCode(String code);
}