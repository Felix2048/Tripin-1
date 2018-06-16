package com.android.tripin.callback;

/**
 * Create by kolos on 2018/6/16.
 * Description:
 */
public interface PersonalFileCallback {
    void onChangeSuccess();
    void onChangeFailed();
    void onConnectFailed();
    void onLogoutSuccess();
}
