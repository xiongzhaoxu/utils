

package com.sinry.exception;


public class BusinessException extends BaseException {
    private static final long serialVersionUID = 1L;

    public BusinessException(String errorCode, String msg) {
        super(errorCode, msg);
    }

    public BusinessException(String errorCode, String message, Throwable throwable) {
        super(errorCode, message, throwable);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(RestExStatus restExStatus) {
        super("B" + restExStatus.getValue(), restExStatus.getReasonPhrase());
    }

    public BusinessException() {
    }
}
