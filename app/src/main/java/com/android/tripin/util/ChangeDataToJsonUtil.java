package com.android.tripin.util;

import com.android.tripin.activity.LoginActivity;
import com.android.tripin.entity.User;
import com.google.gson.Gson;

public  class ChangeDataToJsonUtil {

    static Gson gson = new Gson();
    LoginActivity loginActivity= new LoginActivity();

    public static String GetLoginRequestJson(String userName,String password) {
        User user  = new User(userName,password,userName);
        String jsonLogin = gson.toJson(user);
        return jsonLogin;
    }



}
