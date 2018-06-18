package com.android.tripin.model.interfaces;

import com.android.tripin.callback.SignUpCallback;

public interface ISignUpModel {
    public final static String TAG = ISignUpModel.class.getSimpleName();

    /**
     * 点击注册
     */
    public void signUp(String signUpJson,SignUpCallback signUpCallback);
    public void sendVerificationCode(String phone);
}
