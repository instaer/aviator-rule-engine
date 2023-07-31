package com.github.instaer.ruleengine.controller;

import com.github.instaer.ruleengine.common.vo.ResponseVO;
import com.github.instaer.ruleengine.common.dto.RulesetInfoDTO;
import com.github.instaer.ruleengine.rule.service.RuleCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("api")
@RestController
public class RuleCoreController {

    @Autowired
    private RuleCoreService ruleCoreService;

    @PostMapping("/rulesetInfo/execute")
    public ResponseVO<Map<String, Object>> executeRuleset(@RequestBody RulesetInfoDTO dto) {
        return ResponseVO.ok(ruleCoreService.executeRuleset(dto.getCode(), dto.getParaMap()));
    }
}