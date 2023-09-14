package com.github.instaer.ruleengine.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.Options;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EvaluatorInstanceConfig {

    /**
     * AviatorScript engine instance, the default global shared.
     * Prioritize runtime performance, spend more time optimizing in the compilation phase,
     * <em>suitable for long-running expressions</em> (AviatorEvaluator.EVAL).
     *
     * @return
     */
    @Primary
    @Bean
    AviatorEvaluatorInstance evalEvaluatorInstance(@Value("${aviator.expression-cache-capacity:500}") int capacity) {
        AviatorEvaluatorInstance evaluatorInstance = AviatorEvaluator.getInstance().useLRUExpressionCache(capacity);
        evaluatorInstance.setCachedExpressionByDefault(true);
        evaluatorInstance.setOption(Options.OPTIMIZE_LEVEL, AviatorEvaluator.EVAL);
        // floating point numbers are all parsed as decimal
        evaluatorInstance.setOption(Options.ALWAYS_PARSE_FLOATING_POINT_NUMBER_INTO_DECIMAL, true);
        return evaluatorInstance;
    }

    /**
     * AviatorScript engine instance mainly used for compilation.
     * Prioritize compilation performance, without any compilation optimization, sacrificing certain running performance,
     * suitable for scenarios that require <em>frequent compilation of expressions</em> (AviatorEvaluator.COMPILE).
     *
     * @return
     */
    @Bean
    AviatorEvaluatorInstance compileEvaluatorInstance() {
        AviatorEvaluatorInstance evaluatorInstance = AviatorEvaluator.getInstance();
        evaluatorInstance.setOption(Options.OPTIMIZE_LEVEL, AviatorEvaluator.COMPILE);
        // floating point numbers are all parsed as decimal
        evaluatorInstance.setOption(Options.ALWAYS_PARSE_FLOATING_POINT_NUMBER_INTO_DECIMAL, true);
        return evaluatorInstance;
    }
}