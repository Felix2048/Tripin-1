package com.android.tripin.entity;

import java.io.Serializable;

/**
 * Create by kolos on 2018/6/12.
 * Description:
 */
public class VerificationCode implements Serializable{

    public final static String TAG = VerificationCode.class.getSimpleName();
    private static final long serialVersionUID = 1L;

    String verificationCode;

    public VerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getVerificationCode() {

        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
