package com.android.tripin.presenter;

import android.content.Intent;
import android.widget.Toast;

import com.android.tripin.R;
import com.android.tripin.activity.LauncherActivity;
import com.android.tripin.activity.SignUpActivity;
import com.android.tripin.callback.LoginCallback;
import com.android.tripin.model.LoginModel;
import com.android.tripin.activity.LoginActivity;
import com.android.tripin.presenter.interfaces.ILoginPresenter;
import com.android.tripin.util.ChangeDataToJsonUtil;

public class LoginPresenter implements ILoginPresenter {
    private final static String TAG = LoginPresenter.class.getSimpleName();

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
                loginActivity.hideLoding();
                loginActivity.showResult(R.string.login_success);
                Toast.makeText(loginActivity,"接受成功",Toast.LENGTH_SHORT).show();
                /**
                 * 登陆成功，将登陆状态置为1
                 */
                LauncherActivity.CHECK_LOGIN_STATUS_FLAG=1;
                /**
                 * TODO
                 * 登陆成功还应该创建一个Intent，跳转至个人资料界面或者是主界面
                 */
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
