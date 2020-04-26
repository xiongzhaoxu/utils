package com.sinry.exception;


public class TechnicalException extends BaseRuntimeException {
    private static final long serialVersionUID = 1L;

    public TechnicalException(String errorCode, String msg) {
        super(errorCode, msg);
    }

    public TechnicalException(String errorCode, String message, Throwable throwable) {
        super(errorCode, message, throwable);
    }

    public TechnicalException(RestExStatus restExStatus) {
        super("T" + restExStatus.getValue(), restExStatus.getReasonPhrase());
    }

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException() {
    }
}
