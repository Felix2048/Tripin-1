package com.android.tripin.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.tripin.MainActivity;
import com.android.tripin.R;
import com.android.tripin.base.BaseActivity;
import com.android.tripin.model.SignUpModel;
import com.android.tripin.presenter.SignUpPresenter;
import com.android.tripin.view.ISignUpView;
import com.squareup.haha.perflib.Main;

public class SignUpActivity extends BaseActivity implements ISignUpView, View.OnClickListener{
    private final static String TAG = SignUpActivity.class.getSimpleName();

    private EditText signUpUserName;
    private EditText signUpPassword;
    private EditText signUpConfirmPassword;
    private EditText signUpUserPhone;
    private EditText signUpVerificationCode;
    private Button btnSendVerificationCode;
    private Button btnSignUp;
    private ProgressDialog progressDialog;
    private SignUpPresenter signUpPresenter;

    /**
     * 初始化注册界面布局
     * @return
     */
    void init() {
        signUpUserName = (EditText) findViewById(R.id.sign_up_user_name);
        signUpPassword = (EditText) findViewById(R.id.sign_up_user_password);
        signUpConfirmPassword = (EditText) findViewById(R.id.sign_up_user_confirm_password);
        signUpUserPhone = (EditText) findViewById(R.id.sign_up_user_phone);
        signUpVerificationCode = (EditText) findViewById(R.id.sign_up_verification_code);
        btnSendVerificationCode = (Button) findViewById(R.id.btn_send_verification_code);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up_button);
        progressDialog = new ProgressDialog(this);
        btnSignUp.setOnClickListener(this);
        btnSendVerificationCode.setOnClickListener(this);

    }

    /**
     * 获取signUpPresenter实例
     * @return
     */
    public SignUpPresenter getSignUpPresenter() {
        return new SignUpPresenter(this,new SignUpModel());
    }

    /**
     * 实现注册界面点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_up_button :
                signUpPresenter.signUp();
                break;
            case R.id.btn_send_verification_code:
                signUpPresenter.sendVerificationCode(signUpUserPhone.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    @Override
    protected int getContextViewId() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        signUpPresenter = getSignUpPresenter();
    }

    @Override
    public void showLoding(int msg) {
        progressDialog.setMessage(getString(msg));
        if (!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    @Override
    public void hideLoding() {
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showResult(int result) {
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(int err) {
        Toast.makeText(this,err,Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getUserName() {
        return signUpUserName.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return signUpPassword.getText().toString().trim();
    }

    @Override
    public String getPhone() {
        return signUpUserPhone.getText().toString().trim();
    }

    @Override
    public String getVerificationCode() {
        return signUpVerificationCode.getText().toString().trim();
    }

    /**
     * 以下两个判断尚未确定逻辑
     * @return
     */
    @Override
    public int onJudgeVerificationCode() {
        if(this.getVerificationCode().equals("9527")) {
            return 1;
        }else {
            return 0;
        }

    }

    public void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public int onJudgePasswordEqual() {
        if(this.getPassword().equals(this.getConfirmPassword())) {
            return 1;
        }else {
            return 0;
        }
    }

    public String getConfirmPassword() {
        return signUpConfirmPassword.getText().toString().trim();
    }

    @Override
    protected void onDestroy() {
        if (signUpPresenter!= null) {
            signUpPresenter = null;
        }
        super.onDestroy();
    }

}
