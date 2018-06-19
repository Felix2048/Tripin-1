package com.android.tripin.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tripin.MainActivity;
import com.android.tripin.R;
import com.android.tripin.base.BaseActivity;
import com.android.tripin.manager.DataManager;
import com.android.tripin.model.LoginModel;
import com.android.tripin.presenter.LoginPresenter;
import com.android.tripin.util.ChangeDataToJsonUtil;
import com.android.tripin.view.ILoginView;

public class LoginActivity extends BaseActivity implements ILoginView, View.OnClickListener{

    private final static String TAG = LoginActivity.class.getSimpleName();

    private Button loginButton ;
    private EditText loginUserNameEditText;
    private EditText loginPasswordEditText;
    private TextView createAccountTextView;
    private ProgressDialog progressDialog;
    private LoginPresenter loginPresenter;

    /**
     * 获取用户输入账户
     * @return
     */
    @Override
    public String getUserName() {
        return loginUserNameEditText.getText().toString();
    }

    /**
     * 获取用户密码
     * @return
     */
    @Override
    public String getPassword() {
        return loginPasswordEditText.getText().toString();
    }
    @Override
    protected int getContextViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        loginPresenter = new LoginPresenter(new LoginModel(),this);
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
        loginUserNameEditText.setOnClickListener(this);
        loginPasswordEditText.setOnClickListener(this);
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

    public void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    /**
     * 显示登陆提示框
     * @param msg
     */
    @Override
    public void showLoding(int msg) {
        progressDialog.setMessage(getString(msg));
        if (!progressDialog.isShowing()) {
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
    public void showResult(int result) {
        Looper.prepare();
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示登陆错误内容
     * @param err
     */
    @Override
    public void showError(int err) {
        Toast.makeText(this,err,Toast.LENGTH_SHORT).show();
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
