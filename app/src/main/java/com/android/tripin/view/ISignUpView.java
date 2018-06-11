package com.android.tripin.view;

public interface ISignUpView {
    /**
     * 显示注册进度条
     */
    void showLoding(String msg);

    /**
     * 隐藏注册进度条
     */
    void hideLoding();

    /**
     * 显示注册结果
     * @param result
     */
    void showResult(String result);

    /**
     * 显示错误内容
     * @param err
     */
    void showError(String err);

    /**
     * 获取登陆界面用户名
     * @return
     */
    String getUserName();

    /**
     * 获取登陆界面密码
     * @return
     */
    String getPassword();
    /**
     * 获取注册界面用户手机号
     * @return
     */
    String getPhone();
    /**
     * 获取用户输入的验证码信息
     */
    String getVerificationCode();
    /**
     * 判断用户输入的验证码是否正确
     */
    int onJudgeVerificationCode();
    /**
     * 判断用户两次输入密码是否一致
     */
    int onJudgePasswordEqual();

}
