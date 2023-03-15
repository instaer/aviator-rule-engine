package com.github.instaer.ruleengine.config;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.Options;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AviatorEvaluatorConfig {

    /**
     * 默认的全局共享AviatorScript引擎
     * 默认以运行时性能优先，在编译阶段花费更多时间做优化，适合长期运行的表达式（AviatorEvaluator.EVAL）
     *
     * @return
     */
    @Bean
    @Primary
    AviatorEvaluatorInstance evalEvaluatorInstance() {
        return AviatorEvaluator.getInstance().useLRUExpressionCache(500);
    }

    /**
     * 以编译为主的AviatorScript引擎
     * 以编译性能优先，不做任何编译优化，牺牲一定的运行性能，适合需要频繁编译表达式的场景（AviatorEvaluator.COMPILE）
     *
     * @return
     */
    @Bean
    AviatorEvaluatorInstance compileEvaluatorInstance() {
        AviatorEvaluatorInstance evaluatorInstance = AviatorEvaluator.getInstance();
        evaluatorInstance.setOption(Options.OPTIMIZE_LEVEL, AviatorEvaluator.COMPILE);
        return evaluatorInstance;
    }
}