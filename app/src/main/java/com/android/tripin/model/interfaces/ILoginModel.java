package com.android.tripin.model.interfaces;

import com.android.tripin.callback.LoginCallback;

public interface ILoginModel {
    public final static String TAG = ILoginModel.class.getSimpleName();
    /**
     用户登陆
     */
    public void login(String loginJson, LoginCallback loginCallback);

}
