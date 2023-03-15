package com.github.instaer.ruleengine.aviator;

import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.Expression;
import com.github.instaer.ruleengine.exception.AviatorRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@SuppressWarnings({"unchecked"})
@Component
public class ExpressionExecuteService {

    @Autowired
    private AviatorEvaluatorInstance evalEvaluatorInstance;

    public <T> T execute(String expression, Map<String, Object> paramMap) {
        try {
            log.info("## execute expression start");
            log.info("## execute expression => {}, ", expression);
            log.info("## execute param => {}", paramMap);

            Expression compiledExp = evalEvaluatorInstance.compile(expression, true);
            return (T) compiledExp.execute(paramMap);
        } catch (Exception e) {
            throw new AviatorRuntimeException(e);
        }
    }
}