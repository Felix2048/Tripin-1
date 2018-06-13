package com.android.tripin.callback;

public interface SignUpCallback  {
    public final static String TAG = SignUpCallback.class.getSimpleName();
    /**
     * 用户注册成功时调用
     */
    void onSuccess();
    /**
     * 用户注册失败，该用户名已被使用时调用
     */
    void onUserNameUsed();
    /**
     * 用户注册失败，验证码错误时调用
     */
    void onWrongVerificationCode();
    /**
     * 用户注册失败，两次密码输入不一致时调用
     */
    void onPasswordMismatch();
    /**
     *用户注册失败，手机号已被使用时调用
     */
    void onPhoneUsed();
    /**
     * 网络请求失败时调用
     */
    void onConnectFailed();
    /**
     * 获取验证码成功时调用
     */
    void getVerificationCodeSuccess(String verificationResponse);
    /**
     * 获取验证码失败时调用
     */
    void getVerificationCodeFailed();
}
