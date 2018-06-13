package com.android.tripin.entity;


import java.io.Serializable;

public class UserInfo implements Serializable {
    public final static String TAG = UserInfo.class.getSimpleName();
    private static final long serialVersionUID = 1L;


    private String userName;
    private String password;
    private String phone;
    private String email;
    private int userID;

    /**
     * 此构造方法用于登陆，注册时可能用到的用户名，手机号，密码，提供给ChangeDataToJsonUtil，创建登陆，注册时的json
     * @param userName
     * @param password
     * @param phone
     */
    public UserInfo(String userName, String password, String phone) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
    }



    public UserInfo(String userName, String password, String email, String phone) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
