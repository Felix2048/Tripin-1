package com.android.tripin.entity;


import java.io.Serializable;

public class ResponseMessage implements Serializable {
    public final static String TAG = ResponseMessage.class.getSimpleName();
    private static final long serialVersionUID = 1L;


    String errorCode;
    String msg;
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
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
