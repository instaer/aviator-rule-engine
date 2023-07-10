package com.github.instaer.ruleengine.controller;

import com.github.instaer.ruleengine.common.ConditionInfoDTO;
import com.github.instaer.ruleengine.common.ResponseVO;
import com.github.instaer.ruleengine.common.RuleInfoDTO;
import com.github.instaer.ruleengine.common.RulesetInfoDTO;
import com.github.instaer.ruleengine.constants.ConditionLogicType;
import com.github.instaer.ruleengine.constants.ConditionRelationType;
import com.github.instaer.ruleengine.constants.RuleLogicType;
import com.github.instaer.ruleengine.rule.entity.ConditionInfoEntity;
import com.github.instaer.ruleengine.rule.entity.RuleInfoEntity;
import com.github.instaer.ruleengine.rule.entity.RulesetInfoEntity;
import com.github.instaer.ruleengine.rule.service.RuleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("admin")
@RestController
public class RuleAdminController {

    @Autowired
    private RuleManageService ruleManageService;

    @GetMapping("/conditionLogicType/get")
    public ResponseVO<Map<String, String>> getConditionLogicType() {
        return ResponseVO.ok(ConditionLogicType.getOptions());
    }

    @GetMapping("/conditionRelationType/get")
    public ResponseVO<Map<String, String>> getConditionRelationType() {
        return ResponseVO.ok(ConditionRelationType.getOptions());
    }

    @GetMapping("/ruleLogicType/get")
    public ResponseVO<Map<String, String>> getRuleLogicType() {
        return ResponseVO.ok(RuleLogicType.getOptions());
    }

    @PostMapping("/rulesetInfo/refresh")
    public ResponseVO<String> refreshRulesetInfo(@RequestBody Map<String, Object> requestBody) {
        ruleManageService.refreshRulesetInfo(((Integer) requestBody.get("rulesetId")).longValue());
        return ResponseVO.ok();
    }

    @GetMapping("/rulesetInfo/query")
    public ResponseVO<Page<RulesetInfoEntity>> queryRulesetInfo(RulesetInfoDTO dto) {
        return ResponseVO.ok(ruleManageService.queryRulesetInfo(dto));
    }

    @PostMapping("/rulesetInfo/save")
    public ResponseVO<RulesetInfoEntity> saveRulesetInfo(@RequestBody RulesetInfoEntity rulesetInfoEntity) {
        return ResponseVO.ok(ruleManageService.saveRulesetInfo(rulesetInfoEntity));
    }

    @PostMapping("/rulesetInfo/delete")
    public ResponseVO<String> deleteRulesetInfo(@RequestBody Map<String, Object> requestBody) {
        ruleManageService.deleteRulesetInfo(((Integer) requestBody.get("rulesetId")).longValue());
        return ResponseVO.ok();
    }

    @GetMapping("/ruleInfo/query")
    public ResponseVO<Page<RuleInfoEntity>> queryRuleInfo(RuleInfoDTO dto) {
        return ResponseVO.ok(ruleManageService.queryRuleInfo(dto.getRulesetId(), dto.getPage(), dto.getSize()));
    }

    @PostMapping("/ruleInfo/save")
    public ResponseVO<RuleInfoEntity> saveRuleInfo(@RequestBody RuleInfoEntity ruleInfoEntity) {
        return ResponseVO.ok(ruleManageService.saveRuleInfo(ruleInfoEntity));
    }

    @PostMapping("/ruleInfo/delete")
    public ResponseVO<String> deleteRuleInfo(@RequestBody Map<String, Object> requestBody) {
        ruleManageService.deleteRuleInfo(((Integer) requestBody.get("ruleId")).longValue());
        return ResponseVO.ok();
    }

    @GetMapping("/conditionInfoList/query")
    public ResponseVO<Page<ConditionInfoEntity>> queryConditionInfoList(ConditionInfoDTO dto) {
        return ResponseVO.ok(ruleManageService.queryConditionInfo(dto.getRuleId(), dto.getPage(), dto.getSize()));
    }

    @PostMapping("/conditionInfoList/save")
    public ResponseVO<List<ConditionInfoEntity>> saveConditionList(@RequestBody List<ConditionInfoEntity> conditionInfoEntityList) {
        return ResponseVO.ok(ruleManageService.saveConditionInfoList(conditionInfoEntityList));
    }
}