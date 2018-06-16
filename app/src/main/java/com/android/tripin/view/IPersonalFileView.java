package com.android.tripin.view;

/**
 * Create by kolos on 2018/6/16.
 * Description:
 */
public interface IPersonalFileView {
    /**
     * 显示注销成功提示信息
     */
    void showLogoutSuccess();
    /**
     * 显示修改个人信息成功提示信息
     */
    void showChangeSuccess();
    /**
     * 显示修改个人信息失败提示信息
     */
    void showChangeFailed();
    /**
     * 网络连接失败提示信息
     */
    void showNetworkError();

    /**
     * 获取用户相关信息
     * @return
     */
    String getUserName();
    String getUserPhone();
    String getUserEmail();

}
