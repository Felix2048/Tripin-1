package com.android.tripin.entity;


import java.io.Serializable;

public class ResponseMessage implements Serializable {

    private static final long serialVersionUID = 1L;


    String errorCode;
    Object data;

    public ResponseMessage(String errorCode, Object data) {
        this.errorCode = errorCode;
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
