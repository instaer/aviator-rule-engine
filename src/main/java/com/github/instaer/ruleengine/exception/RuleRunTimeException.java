package com.github.instaer.ruleengine.exception;

@SuppressWarnings("unused")
public class RuleRunTimeException extends RuntimeException {

    public RuleRunTimeException() {
        super();
    }

    public RuleRunTimeException(String message) {
        super(message);
    }

    public RuleRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleRunTimeException(Throwable cause) {
        super(cause);
    }

    protected RuleRunTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}