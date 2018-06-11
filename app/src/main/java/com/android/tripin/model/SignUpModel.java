package com.android.tripin.model;

import com.android.tripin.callback.SignUpCallback;
import com.android.tripin.model.interfaces.ISignUpModel;
import com.android.tripin.util.OkHttp3Util;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.android.tripin.model.LoginModel.JSON;
import static com.android.tripin.util.ParserJsonToDataUtil.getLoginResponseMessage;


public class SignUpModel implements ISignUpModel {


    public OkHttpClient client = OkHttp3Util.getClient();

    /**
     * 发送注册网络请求，获取反馈信息
     * @param signUpJson
     * @param signUpCallback
     */
    @Override
    public void signUp(String signUpJson,SignUpCallback signUpCallback) {
        RequestBody signUpRequestBody = RequestBody.create(JSON,signUpJson);
        Request signUpRequest = new Request.Builder()
                .url("")
                .post(signUpRequestBody)
                .build();
        client.newCall(signUpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                signUpCallback.onConnectFailed();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String signUpResponse = response.body().string();
                String errorCode = getLoginResponseMessage(signUpResponse);
                switch (errorCode){
                    case "0000" :
                        signUpCallback.onSuccess();
                        break;
                    case "1002" :
                        signUpCallback.onUserNameUsed();
                        break;
                    case "1003":
                        signUpCallback.onPhoneUsed();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 发送获取验证码请求，取得验证码存储至本地
     * @param sendVerificationCodeJson
     * @param signUpCallback
     */
    @Override
    public void sendVerificationCode(String sendVerificationCodeJson, SignUpCallback signUpCallback) {
        Request sendVerificationCodeRequest= new Request.Builder()
                .url("")
                .build();
        client.newCall(sendVerificationCodeRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                signUpCallback.onConnectFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String sendVerificationCodeResponse = response.body().string();

            }
        });
    }

}
