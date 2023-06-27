package com.github.instaer.ruleengine.aviator;

import com.github.instaer.ruleengine.exception.AviatorRuntimeException;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.Expression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@SuppressWarnings({"unchecked"})
@Component
public class ExpressionExecuteService {

    @Autowired
    private AviatorEvaluatorInstance evalEvaluatorInstance;

    public <T> T execute(String expression, Map<String, Object> paramMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            Thread.sleep(3000);
            log.info("## execute expression start");
            log.info("## execute expression =>\n {}, ", expression);
            log.info("## execute param => {}", paramMap);

            Expression compiledExp = evalEvaluatorInstance.compile(expression, true);
            return (T) compiledExp.execute(paramMap);
        } catch (Exception e) {
            throw new AviatorRuntimeException(e);
        } finally {
            stopWatch.stop();
            log.info("## execute expression end (elapsed {} milliseconds)", stopWatch.getTime(TimeUnit.MILLISECONDS));
        }
    }
}