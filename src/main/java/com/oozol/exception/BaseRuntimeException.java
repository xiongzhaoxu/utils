
package com.oozol.exception;

public class BaseRuntimeException extends RuntimeException {
    private String errorCode;

    public BaseRuntimeException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseRuntimeException(String errorCode, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
    }

    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException() {
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
