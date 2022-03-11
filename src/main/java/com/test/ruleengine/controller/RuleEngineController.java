package com.test.ruleengine.controller;

import com.test.ruleengine.common.ConditionInfoDTO;
import com.test.ruleengine.common.RuleInfoDTO;
import com.test.ruleengine.constants.ConditionLogicType;
import com.test.ruleengine.constants.ConditionOperateType;
import com.test.ruleengine.common.ResponseVO;
import com.test.ruleengine.rule.entity.ConditionInfoEntity;
import com.test.ruleengine.rule.entity.RuleInfoEntity;
import com.test.ruleengine.rule.service.RuleCoreService;
import com.test.ruleengine.rule.service.RuleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RuleEngineController {

    @Autowired
    private RuleManageService ruleManageService;

    @Autowired
    private RuleCoreService ruleCoreService;

    @GetMapping("logicTypeMap")
    public ResponseVO<Map<Integer, String>> logicTypeMap() {
        return ResponseVO.ok(ConditionLogicType.conditionLogicTypeMap);
    }

    @GetMapping("/operateTypeMap")
    public ResponseVO<Map<Integer, String>> operateTypeMap() {
        return ResponseVO.ok(ConditionOperateType.conditionOperateTypeMap);
    }

    @GetMapping("/findRuleInfoPage")
    public ResponseVO<Page<RuleInfoEntity>> findRuleInfoPage(RuleInfoDTO dto) {
        return ResponseVO.ok(ruleManageService.findRuleInfoPage(dto.getPage(), dto.getSize()));
    }

    @GetMapping("/findConditionInfoPage")
    public ResponseVO<Page<ConditionInfoEntity>> findConditionInfoPage(ConditionInfoDTO dto) {
        return ResponseVO.ok(ruleManageService.findConditionInfoPage(dto.getRuleId(), dto.getPage(), dto.getSize()));
    }

    @PostMapping("/saveRuleInfo")
    public ResponseVO<?> saveRuleInfo(@RequestBody RuleInfoEntity ruleInfoEntity) {
        return ResponseVO.ok(ruleManageService.saveRuleInfo(ruleInfoEntity));
    }

    @PostMapping("/saveConditionInfoList")
    public ResponseVO<String> saveConditionInfoList(@RequestBody List<ConditionInfoEntity> conditionInfoEntityList) {
        ruleManageService.saveConditionInfoList(conditionInfoEntityList);
        return ResponseVO.ok();
    }

    @PostMapping("/deleteRuleInfo")
    public ResponseVO<String> deleteRuleInfo(Long ruleId) {
        ruleManageService.deleteRuleInfo(ruleId);
        return ResponseVO.ok();
    }

    @PostMapping("/executeRule")
    public ResponseVO<Boolean> executeRule(@RequestBody RuleInfoDTO dto) {
        return ResponseVO.ok(ruleCoreService.executeRule(dto.getRuleCode(), dto.getParamMap()));
    }

    @PostMapping("/refreshRuleExpression")
    public ResponseVO<String> refreshRuleExpression(Long ruleId) {
        ruleManageService.refreshRuleExpression(ruleId);
        return ResponseVO.ok();
    }
}