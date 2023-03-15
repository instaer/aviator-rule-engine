package com.github.instaer.ruleengine.controller;

import com.github.instaer.ruleengine.common.ConditionInfoDTO;
import com.github.instaer.ruleengine.common.ResponseVO;
import com.github.instaer.ruleengine.common.RuleInfoDTO;
import com.github.instaer.ruleengine.common.RulesetInfoDTO;
import com.github.instaer.ruleengine.constants.ConditionLogicType;
import com.github.instaer.ruleengine.constants.ConditionRelationType;
import com.github.instaer.ruleengine.rule.entity.ConditionInfoEntity;
import com.github.instaer.ruleengine.rule.entity.RuleInfoEntity;
import com.github.instaer.ruleengine.rule.entity.RulesetInfoEntity;
import com.github.instaer.ruleengine.rule.service.RuleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RuleManageController {

    @Autowired
    private RuleManageService ruleManageService;

    @GetMapping("logicTypeMap")
    public ResponseVO<Map<Integer, String>> logicTypeMap() {
        return ResponseVO.ok(ConditionLogicType.conditionLogicTypeMap);
    }

    @GetMapping("/relationTypeMap")
    public ResponseVO<Map<Integer, String>> relationTypeMap() {
        return ResponseVO.ok(ConditionRelationType.conditionRelationTypeMap);
    }

    @GetMapping("/findRulesetInfoPage")
    public ResponseVO<Page<RulesetInfoEntity>> findRulesetInfoPage(RulesetInfoDTO dto) {
        return ResponseVO.ok(ruleManageService.findRulesetInfoPage(dto.getPage(), dto.getSize()));
    }

    @GetMapping("/findRuleInfoPage")
    public ResponseVO<Page<RuleInfoEntity>> findRuleInfoPage(RuleInfoDTO dto) {
        return ResponseVO.ok(ruleManageService.findRuleInfoPage(dto.getRulesetId(), dto.getPage(), dto.getSize()));
    }

    @GetMapping("/findConditionInfoPage")
    public ResponseVO<Page<ConditionInfoEntity>> findConditionInfoPage(ConditionInfoDTO dto) {
        return ResponseVO.ok(ruleManageService.findConditionInfoPage(dto.getRuleId(), dto.getPage(), dto.getSize()));
    }

    @PostMapping("/saveRulesetInfo")
    public ResponseVO<?> saveRulesetInfo(@RequestBody RulesetInfoEntity rulesetInfoEntity) {
        return ResponseVO.ok(ruleManageService.saveRulesetInfo(rulesetInfoEntity));
    }

    @PostMapping("/deleteRulesetInfo")
    public ResponseVO<String> deleteRulesetInfo(Long rulesetId) {
        ruleManageService.deleteRulesetInfo(rulesetId);
        return ResponseVO.ok();
    }

    @PostMapping("/saveRuleInfo")
    public ResponseVO<?> saveRuleInfo(@RequestBody RuleInfoEntity ruleInfoEntity) {
        return ResponseVO.ok(ruleManageService.saveRuleInfo(ruleInfoEntity));
    }

    @PostMapping("/deleteRuleInfo")
    public ResponseVO<String> deleteRuleInfo(Long ruleId) {
        ruleManageService.deleteRuleInfo(ruleId);
        return ResponseVO.ok();
    }

    @PostMapping("/saveConditionInfoList")
    public ResponseVO<String> saveConditionInfoList(@RequestBody List<ConditionInfoEntity> conditionInfoEntityList) {
        ruleManageService.saveConditionInfoList(conditionInfoEntityList);
        return ResponseVO.ok();
    }

    @PostMapping("/refreshRuleset")
    public ResponseVO<String> refreshRuleset(Long rulesetId) {
        ruleManageService.refreshRuleset(rulesetId);
        return ResponseVO.ok();
    }
}