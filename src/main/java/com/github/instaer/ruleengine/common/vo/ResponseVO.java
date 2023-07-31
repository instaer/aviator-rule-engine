package com.github.instaer.ruleengine.common.vo;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVO<T> {
    private int status;
    private boolean success;
    private String message;
    private T body;

    public static <T> ResponseVO<T> ok() {
        return ok(null);
    }

    public static <T> ResponseVO<T> ok(T body) {
        return new ResponseVO<>(HttpStatus.OK.value(), true, null, body);
    }

    public static <T> ResponseVO<T> error(String message) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    public static <T> ResponseVO<T> error(int status, String message) {
        return new ResponseVO<>(status, false, message, null);
    }
}