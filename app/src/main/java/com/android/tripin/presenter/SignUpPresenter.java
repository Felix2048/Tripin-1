package com.android.tripin.presenter;

import com.android.tripin.activity.SignUpActivity;
import com.android.tripin.model.SignUpModel;
import com.android.tripin.presenter.interfaces.ISignUpPresenter;

public class SignUpPresenter implements ISignUpPresenter {

    private final static String TAG = SignUpPresenter.class.getSimpleName();

    private SignUpModel loginModel;
    private SignUpActivity loginActivity;

    public SignUpPresenter(SignUpModel loginModel, SignUpActivity loginActivity) {
        this.loginModel = loginModel;
        this.loginActivity = loginActivity;
    }


    @Override
    public void signUp() {

    }

    @Override
    public void sendVerificationCode() {

    }
}
