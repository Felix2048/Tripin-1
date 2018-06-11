package com.android.tripin.util;

import com.android.tripin.activity.LoginActivity;
import com.android.tripin.entity.UserInfo;
import com.google.gson.Gson;

public  class ChangeDataToJsonUtil {

    private static Gson gson = new Gson();
    LoginActivity loginActivity= new LoginActivity();

    public static String GetLoginRequestJson(String userName,String password) {
        UserInfo userInfo = new UserInfo(userName,password,userName);
        String jsonLogin = gson.toJson(userInfo);
        return jsonLogin;
    }



}
