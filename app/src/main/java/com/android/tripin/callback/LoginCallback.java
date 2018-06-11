package com.android.tripin.callback;

public interface LoginCallback {
    public final static String TAG = LoginCallback.class.getSimpleName();
    /**
     * 用户登陆成功时调用
     */

    void onSuccess();
    /**
     * 用户登陆失败时调用
     */
    void onFailure();
    /**
     * 用户账户被禁用时调用
     */
    void onAccountBanned();
    /**
     * 网络请求失败时调用
     */
    void onConnectFailed();
}
