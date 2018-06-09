package com.android.tripin.presenter;

import com.android.tripin.callback.LoginCallback;
import com.android.tripin.model.LoginModel;
import com.android.tripin.view.LoginActivity;

public class LoginPresenter implements ILoginPresenter{

    private LoginModel loginModel;
    private LoginActivity loginActivity;

    public LoginPresenter(LoginModel loginModel, LoginActivity loginActivity) {
        this.loginModel = loginModel;
        this.loginActivity = loginActivity;
    }

    @Override
    public void login() {
        loginActivity.showLoding("登陆中...");
        loginModel.login(loginActivity.getUserName(), loginActivity.getPassword(), new LoginCallback() {
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

    @Override
    public void createAccount() {
        /**
         * 创建一个Intent，跳转到注册界面
         */
    }
}
