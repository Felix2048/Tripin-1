package com.android.tripin.presenter;

import android.content.Intent;

import com.android.tripin.activity.SignUpActivity;
import com.android.tripin.callback.LoginCallback;
import com.android.tripin.model.LoginModel;
import com.android.tripin.activity.LoginActivity;
import com.android.tripin.presenter.interfaces.ILoginPresenter;
import com.android.tripin.util.ChangeDataToJsonUtil;

public class LoginPresenter implements ILoginPresenter {

    private LoginModel loginModel;
    private LoginActivity loginActivity;

    public LoginPresenter(LoginModel loginModel, LoginActivity loginActivity) {
        this.loginModel = loginModel;
        this.loginActivity = loginActivity;
    }

    /**
     * 获取用户名，密码，将数据转化成loginJson
     */
    String loginJson = ChangeDataToJsonUtil.GetLoginRequestJson(loginActivity.getUserName(),loginActivity.getPassword());

    @Override
    public void login() {
        loginActivity.showLoding("登陆中...");
        /**
         * 传入loginJson,处理返回结果
         */
        loginModel.login(loginJson, new LoginCallback() {
            @Override
            public void onSuccess() {
                loginActivity.hideLoding();
                loginActivity.showResult("登陆成功");
                /**
                 * 登陆成功还应该创建一个Intent，跳转至个人资料界面或者是主界面
                 */
            }

            @Override
            public void onFailure() {
                loginActivity.hideLoding();
                loginActivity.showError("登陆失败");
            }

            @Override
            public void onAccountBanned() {
                loginActivity.hideLoding();
                loginActivity.showError("账户被禁用");
            }

            @Override
            public void onConnectFailed() {
                loginActivity.hideLoding();
                loginActivity.showError("登陆失败，网络连接错误");
            }
        });
    }

    /**
     * 创建一个Intent，跳转到注册界面
     */
    @Override
    public void createAccount() {
        Intent intent = new Intent(loginActivity, SignUpActivity.class);
        loginActivity.startActivity(intent);
    }
}
