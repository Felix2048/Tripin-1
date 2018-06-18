package com.android.tripin.util;

import com.android.tripin.activity.LoginActivity;
import com.android.tripin.activity.SignUpActivity;
import com.android.tripin.entity.UserInfo;
import com.google.gson.Gson;

public  class ChangeDataToJsonUtil {
    private final static String TAG = ChangeDataToJsonUtil.class.getSimpleName();

    private static Gson gson = new Gson();
    LoginActivity loginActivity= new LoginActivity();
    SignUpActivity signUpActivity = new SignUpActivity();

    /**
     * 将用户输入的用户名密码，转化成JSON数据，其中userName使用两次，第一次用于匹配userName，第二次用于匹配手机号
     * @param userName
     * @param password
     * @return
     */
    public static String getLoginRequestJson(String userName, String password) {
        UserInfo userInfo = new UserInfo(userName,password,userName);
        String jsonLogin = gson.toJson(userInfo);
        return jsonLogin;
    }

    /**
     * 将用户输入的搜索信息封装成json
     */

    public static String parseSearchInfoToJson(String userInfo) {
        UserInfo userInfo1 = new UserInfo(userInfo,userInfo);
        String jsonSearch = gson.toJson(userInfo1);
        return jsonSearch;
    }
    /**
     * 将用户输入的用户名，密码，手机号转化成JSON数据，供注册使用
     * @param userName
     * @param password
     * @param phone
     * @return
     */
    public static String getSignUpRequestJson(String userName,String password,String phone) {
        UserInfo userInfo = new UserInfo(userName,password,phone);
        String jsonSignUp = gson.toJson(userInfo);
        return  jsonSignUp;
    }

    public static String getChangePersonalFileRequestJson(String userName,String email,String phone) {
        UserInfo userInfo = UserInfo.getChangeFileUser(userName,email,phone);
        String jsonChangePersonalFile = gson.toJson(userInfo);
        return  jsonChangePersonalFile;
    }


}
