package com.github.instaer.ruleengine.controller;

import com.github.instaer.ruleengine.common.dto.ConditionInfoDTO;
import com.github.instaer.ruleengine.common.dto.RuleInfoDTO;
import com.github.instaer.ruleengine.common.dto.RulesetInfoDTO;
import com.github.instaer.ruleengine.common.vo.ConditionInfoVO;
import com.github.instaer.ruleengine.common.vo.ResponseVO;
import com.github.instaer.ruleengine.common.vo.RuleInfoVO;
import com.github.instaer.ruleengine.common.vo.RulesetInfoVO;
import com.github.instaer.ruleengine.constants.ConditionLogicType;
import com.github.instaer.ruleengine.constants.ConditionRelationType;
import com.github.instaer.ruleengine.constants.RuleLogicType;
import com.github.instaer.ruleengine.rule.RuleManageService;
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
    public ResponseVO<String> refreshRulesetInfo(@RequestBody RulesetInfoDTO dto) {
        ruleManageService.refreshRulesetInfo(dto);
        return ResponseVO.ok();
    }

    @GetMapping("/rulesetInfo/query")
    public ResponseVO<Page<RulesetInfoVO>> queryRulesetInfoPage(RulesetInfoDTO dto) {
        return ResponseVO.ok(ruleManageService.queryRulesetInfoPage(dto));
    }

    @GetMapping("/rulesetInfo/detail")
    public ResponseVO<RulesetInfoVO> queryRulesetInfoDetail(RulesetInfoDTO dto) {
        return ResponseVO.ok(ruleManageService.queryRulesetInfoDetail(dto));
    }

    @PostMapping("/rulesetInfo/save")
    public ResponseVO<RulesetInfoVO> saveRulesetInfo(@RequestBody RulesetInfoDTO dto) {
        return ResponseVO.ok(ruleManageService.saveRulesetInfo(dto));
    }

    @PostMapping("/rulesetInfo/delete")
    public ResponseVO<String> deleteRulesetInfo(@RequestBody RulesetInfoDTO dto) {
        ruleManageService.deleteRulesetInfo(dto);
        return ResponseVO.ok();
    }

    @GetMapping("/ruleInfo/query")
    public ResponseVO<Page<RuleInfoVO>> queryRuleInfoPage(RuleInfoDTO dto) {
        return ResponseVO.ok(ruleManageService.queryRuleInfoPage(dto));
    }

    @PostMapping("/ruleInfo/save")
    public ResponseVO<RuleInfoVO> saveRuleInfo(@RequestBody RuleInfoDTO dto) {
        return ResponseVO.ok(ruleManageService.saveRuleInfo(dto));
    }

    @PostMapping("/ruleInfo/delete")
    public ResponseVO<String> deleteRuleInfo(@RequestBody RuleInfoDTO dto) {
        ruleManageService.deleteRuleInfo(dto);
        return ResponseVO.ok();
    }

    @GetMapping("/conditionInfoList/query")
    public ResponseVO<List<ConditionInfoVO>> queryConditionInfoList(ConditionInfoDTO dto) {
        return ResponseVO.ok(ruleManageService.queryConditionInfoList(dto));
    }

    @PostMapping("/conditionInfoList/save")
    public ResponseVO<List<ConditionInfoVO>> saveConditionInfoList(@RequestBody List<ConditionInfoDTO> dtoList) {
        return ResponseVO.ok(ruleManageService.saveConditionInfoList(dtoList));
    }
}