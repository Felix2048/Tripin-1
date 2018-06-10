package com.android.tripin.entity;

public class User {
    private String userName;
    private String password;
    private String phone;
    private String email;

    /**
     * 此构造方法用于登陆时可能用到的用户名，手机号，密码，提供给ChangeDataToJsonUtil，创建登陆时的json
     * @param userName
     * @param password
     * @param phone
     */
    public User(String userName, String password, String phone) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
    }



    public User(String userName, String password, String email, String phone) {
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
