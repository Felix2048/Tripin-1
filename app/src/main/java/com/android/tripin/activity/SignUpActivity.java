package com.android.tripin.activity;

<<<<<<< HEAD
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
=======
>>>>>>> 644eebe3424926a0fc84f5d3a6184ffeba6fdb1c
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.tripin.R;
import com.android.tripin.base.BaseActivity;
<<<<<<< HEAD
import com.android.tripin.view.ISignUpView;

public class SignUpActivity extends BaseActivity implements ISignUpView{

    private EditText signUpUserName;
    private EditText signUpPassword;
    private EditText signUpConfirmPassword;
    private EditText signUpUserPhone;
    private EditText signUpVerificationCode;
    private Button btnSendVerificationCode;
    private Button btnSignUp;
    private ProgressDialog progressDialog;

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
    }

    @Override
    protected int getContextViewId() {
        return 0;
=======

public class SignUpActivity extends BaseActivity {

    @Override
    protected int getContextViewId() {
        return R.layout.activity_sign_up;
>>>>>>> 644eebe3424926a0fc84f5d3a6184ffeba6fdb1c
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    public void showLoding(String msg) {
        progressDialog.setMessage(msg);
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
    public void showResult(String result) {
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String err) {
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

    public String getConfirmPassword() {
        return signUpConfirmPassword.getText().toString().trim();
    }
}
