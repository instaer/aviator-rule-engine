package com.github.instaer.ruleengine.exception;

@SuppressWarnings("unused")
public class AviatorRuntimeException extends RuntimeException {

    public AviatorRuntimeException() {
    }

    public AviatorRuntimeException(String message) {
        super(message);
    }

    public AviatorRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AviatorRuntimeException(Throwable cause) {
        super(cause);
    }

    public AviatorRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}