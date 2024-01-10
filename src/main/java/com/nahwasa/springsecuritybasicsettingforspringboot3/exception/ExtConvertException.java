package com.nahwasa.springsecuritybasicsettingforspringboot3.exception;

public class ExtConvertException extends RuntimeException {

    public ExtConvertException() {
        super();
    }

    public ExtConvertException(String message) {
        super(message);
    }

    public ExtConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExtConvertException(Throwable cause) {
        super(cause);
    }

    protected ExtConvertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
