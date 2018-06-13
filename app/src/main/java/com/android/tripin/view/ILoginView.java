package com.android.tripin.view;

public interface ILoginView {
    /**
     * 显示登陆进度条
     */
    void showLoding(int msg);

    /**
     * 隐藏登陆进度条
     */
    void hideLoding();

    /**
     * 显示登陆结果
     * @param result
     */
    void showResult(int  result);

    /**
     * 显示错误内容
     * @param err
     */
    void showError(int err);

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
