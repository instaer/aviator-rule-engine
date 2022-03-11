package com.test.ruleengine.rule.repository;

import com.test.ruleengine.rule.entity.RuleInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleInfoRepository extends JpaRepository<RuleInfoEntity, Long> {

    RuleInfoEntity findByCode(String code);
}