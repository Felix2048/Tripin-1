package com.android.tripin.view;

public interface ILoginView {
    /**
     * 显示登陆进度条
     */
    void showLoding(String msg);

    /**
     * 隐藏登陆进度条
     */
    void hideLoding();

    /**
     * 显示登陆结果
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
}
