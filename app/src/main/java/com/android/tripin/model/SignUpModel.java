package com.android.tripin.model;

import com.android.tripin.callback.SignUpCallback;
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

    @Override
    public void signUp(String signUpJson,SignUpCallback signUpCallback) {
        RequestBody loginRequestBody = RequestBody.create(JSON,signUpJson);
        Request loginRequest = new Request.Builder()
                .url("")
                .post(loginRequestBody)
                .build();
        client.newCall(loginRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                loginCallback.onConnectFailed();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String loginResponse = response.body().string();
                String errorCode = getLoginResponseMessage(loginResponse);
                switch (errorCode){
                    case "0" :
                        loginCallback.onSuccess();
                        break;
                    case "400" :
                        loginCallback.onFailure();
                        break;
                    case "401":
                        loginCallback.onAccountBanned();
                        break;
                    default:
                        break;
                }
            }
        });
    }
    
}
