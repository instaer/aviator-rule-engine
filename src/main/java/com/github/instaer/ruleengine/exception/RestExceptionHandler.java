package com.github.instaer.ruleengine.exception;

import com.github.instaer.ruleengine.common.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

    @ExceptionHandler(RuleRunTimeException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseVO<?> exceptionHandler(RuleRunTimeException e) {
        log.error("Rule RunTimeException", e);
        return ResponseVO.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseVO<?> exceptionHandler(Exception e) {
        log.error("Server RunTimeException", e);
        return ResponseVO.error(e.getMessage());
    }
}