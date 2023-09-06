package com.github.instaer.ruleengine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping({"/", "/static"})
@Controller
public class PageController {

    @RequestMapping(value = "*")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/rule-info")
    public ModelAndView ruleInfo() {
        return new ModelAndView("rule-info");
    }

    @RequestMapping(value = "/ruleset-info")
    public ModelAndView rulesetInfo() {
        return new ModelAndView("ruleset-info");
    }
}
