
package com.sinry.utils;

import lombok.Data;

@Data
public class RestResult<T> {
    private String msg;
    private Boolean succeed = true;
    private T data;
    private String code = "200";
    private String requestId;

    public RestResult() {
    }

    public String getMsg() {
        return this.msg;
    }

    public Boolean getSucceed() {
        return this.succeed;
    }

    public T getData() {
        return this.data;
    }

    public String getCode() {
        return this.code;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSucceed(Boolean succeed) {
        this.succeed = succeed;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
