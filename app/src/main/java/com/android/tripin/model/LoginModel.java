package com.android.tripin.model;

import com.android.tripin.callback.LoginCallback;
import com.android.tripin.util.OkHttp3;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginModel implements ILoginModel {

    /**
     * 获取OkHttp实例
     */
    public OkHttpClient client = OkHttp3.getClient();

    /**
     * 发送HTTP登陆请求，通过回调接口处理返回结果
     * @param account
     * @param password
     * @param loginCallback
     */
    @Override
    public void login(String account, String password, LoginCallback loginCallback) {

                FormBody.Builder loginBuilder = new FormBody.Builder();
                RequestBody loginRequestBody = loginBuilder
                        .add("userName",account)
                        .add("password",password)
                        .build();
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
                        switch (loginResponse){
                            case "SUCCESS" :
                                loginCallback.onSuccess();
                                break;
                            case "FAILURE" :
                                loginCallback.onFailure();
                                break;
                            case "ACCOUNT_BANNED":
                                loginCallback.onAccountBanned();
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
}
