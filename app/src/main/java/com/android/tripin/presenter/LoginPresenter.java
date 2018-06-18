package com.android.tripin.presenter;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.tripin.R;
import com.android.tripin.activity.LauncherActivity;
import com.android.tripin.activity.SignUpActivity;
import com.android.tripin.callback.LoginCallback;
import com.android.tripin.manager.DataManager;
import com.android.tripin.model.LoginModel;
import com.android.tripin.activity.LoginActivity;
import com.android.tripin.presenter.interfaces.ILoginPresenter;
import com.android.tripin.util.ChangeDataToJsonUtil;

public class LoginPresenter implements ILoginPresenter {
    private final static String TAG = LoginPresenter.class.getSimpleName();
    DataManager dataManager = new DataManager();
    private LoginModel loginModel;
    private LoginActivity loginActivity;
    String loginJson;

    public LoginPresenter(LoginModel loginModel, LoginActivity loginActivity) {
        this.loginModel = loginModel;
        this.loginActivity = loginActivity;
        loginJson = ChangeDataToJsonUtil.getLoginRequestJson(loginActivity.getUserName(),loginActivity.getPassword());
    }

    /**
     * 获取用户名，密码，将数据转化成loginJson
     */



    /**
     * 登陆
     */
    @Override
    public void login() {
        loginActivity.showLoding(R.string.login_ing);
        /**
         * 传入loginJson,处理返回结果
         */
        loginModel.login(loginJson, new LoginCallback() {
            @Override
            public void onSuccess() {
                DataManager.setIsLogin(true);
                loginActivity.hideLoding();
                loginActivity.showResult(R.string.login_success);
                loginActivity.goHome();
            }

            @Override
            public void onFailure() {
                loginActivity.hideLoding();
                loginActivity.showError(R.string.login_failed);
            }

            @Override
            public void onAccountBanned() {
                loginActivity.hideLoding();
                loginActivity.showError(R.string.login_account_banned);
            }

            @Override
            public void onConnectFailed() {
                loginActivity.hideLoding();
                loginActivity.showError(R.string.login_network_error);
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
