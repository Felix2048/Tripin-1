package com.android.tripin.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.http.MethodType;
import com.android.tripin.R;
import com.android.tripin.activity.SignUpActivity;
import com.android.tripin.callback.SignUpCallback;
import com.android.tripin.manager.DataManager;
import com.android.tripin.model.SignUpModel;
import com.android.tripin.presenter.interfaces.ISignUpPresenter;
import com.android.tripin.util.ChangeDataToJsonUtil;


public class SignUpPresenter implements ISignUpPresenter{

    private final static String TAG = SignUpPresenter.class.getSimpleName();

    SignUpActivity signUpActivity = new SignUpActivity();
    SignUpModel signUpModel = new SignUpModel();

    public SignUpPresenter(SignUpActivity signUpActivity, SignUpModel signUpModel) {
        this.signUpActivity = signUpActivity;
        this.signUpModel = signUpModel;
        signUpJson = ChangeDataToJsonUtil.getSignUpRequestJson(signUpActivity.getUserName(),signUpActivity.getPassword(),signUpActivity.getPhone());
        signUpCallback = new SignUpCallback() {
            @Override
            public void onSuccess() {
                signUpActivity.hideLoding();
                signUpActivity.showResult(R.string.sign_up_success);
                DataManager.setIsLogin(true);
                signUpActivity.goHome();
            }

            @Override
            public void onUserNameUsed() {
                signUpActivity.hideLoding();
                signUpActivity.showError(R.string.sign_up_user_name_used);
            }

            @Override
            public void onWrongVerificationCode() {
                signUpActivity.hideLoding();
                signUpActivity.showError(R.string.sign_up_wrong_verification_code);
            }

            @Override
            public void onPasswordMismatch() {
                signUpActivity.hideLoding();
                signUpActivity.showError(R.string.sign_up_password_mispatch);
            }

            @Override
            public void onPhoneUsed() {
                signUpActivity.hideLoding();
                signUpActivity.showError(R.string.sign_up_phone_used);
            }

            @Override
            public void onConnectFailed() {
                signUpActivity.hideLoding();
                signUpActivity.showError(R.string.sign_up_network_error);
            }

            @Override
            public void getVerificationCodeSuccess(String verificationResponse) {
                SharedPreferences.Editor storeVerificationCode = signUpActivity.getSharedPreferences("sign_up_verification_code", Context.MODE_PRIVATE).edit();
                storeVerificationCode.putString("verificationCode",verificationResponse);
                storeVerificationCode.apply();
            }

            @Override
            public void getVerificationCodeFailed() {
                signUpActivity.hideLoding();
                signUpActivity.showError(R.string.sign_up_get_verification_code_failed);
            }
        };
    }

    /**
     * 获取用户注册所需数据，转换成JSON数据
     */
    private String signUpJson;

    /**
     * 获取SignCallback接口实例
     */
    SignUpCallback signUpCallback;


    /**
     * 用户注册
     */
    @Override
    public void signUp() {
        if (signUpActivity.onJudgePasswordEqual()==1 && signUpActivity.onJudgeVerificationCode()==1) {
            signUpModel.signUp(signUpJson,signUpCallback);
        }

    }

    /**
     * 发送验证码
     */
    @Override
    public void sendVerificationCode(String phone) {
       signUpModel.sendVerificationCode(phone);
    }

}
