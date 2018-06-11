package com.android.tripin.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tripin.R;
import com.android.tripin.base.BaseActivity;
import com.android.tripin.model.LoginModel;
import com.android.tripin.presenter.LoginPresenter;
import com.android.tripin.view.ILoginView;

public class LoginActivity extends BaseActivity implements ILoginView, View.OnClickListener{

    private Button loginButton ;
    private EditText loginUserNameEditText;
    private EditText loginPasswordEditText;
    private TextView createAccountTextView;
    private ProgressDialog progressDialog;
    private LoginPresenter loginPresenter;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        loginPresenter = initLoginPresenter();
    }

    /**
     * 初始化登陆界面布局
     */
    private void initView() {
        loginButton = (Button) findViewById(R.id.btn_login_button);
        loginUserNameEditText = (EditText) findViewById(R.id.login_user_name);
        loginPasswordEditText = (EditText)findViewById(R.id.login_password);
        createAccountTextView = (TextView) findViewById(R.id.create_account);
        progressDialog = new ProgressDialog(this);
        loginButton.setOnClickListener(this);
        createAccountTextView.setOnClickListener(this);
    }

    /**
     * 初始化LoginPresenter
     */

    public LoginPresenter initLoginPresenter() {
        return new LoginPresenter(new LoginModel(),this);
    }

    /**
     * 处理登陆界面点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_button:
                loginPresenter.login();
                break;
            case R.id.create_account:
                loginPresenter.createAccount();
                break;
            default:
                break;
        }
    }

    /**
     * 显示登陆提示框
     * @param msg
     */
    @Override
    public void showLoding(String msg) {
        progressDialog.setMessage(msg);
        if (!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    /**
     * 隐藏登陆提示框
     */
    @Override
    public void hideLoding() {
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    /**
     * 显示登陆成功结果
     * @param result
     */
    @Override
    public void showResult(String result) {
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示登陆错误内容
     * @param err
     */
    @Override
    public void showError(String err) {
        Toast.makeText(this,err,Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取用户输入账户
     * @return
     */
    @Override
    public String getUserName() {
        return loginUserNameEditText.getText().toString().trim();
    }

    /**
     * 获取用户密码
     * @return
     */
    @Override
    public String getPassword() {
        return loginPasswordEditText.getText().toString().trim();
    }

    /**
     * 销毁presenter
     */
    @Override
    protected void onDestroy() {
        if (loginPresenter!=null) {
            loginPresenter = null;
        }
        super.onDestroy();
    }
}
