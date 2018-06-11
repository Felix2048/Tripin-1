package com.android.tripin.model;

import com.android.tripin.callback.LoginCallback;
import com.android.tripin.model.interfaces.ILoginModel;
import com.android.tripin.util.OkHttp3Util;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.android.tripin.util.ParserJsonToDataUtil.getLoginResponseMessage;

public class LoginModel implements ILoginModel {

    /**
     * 获取OkHttp实例
     */
    private final static String TAG = LoginModel.class.getSimpleName();

    public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
    public OkHttpClient client = OkHttp3Util.getClient();

    /**
     * 发送HTTP登陆请求，通过回调接口处理返回结果
     * @param loginJson
     * @param loginCallback
     */
    @Override
    public void login(String loginJson, LoginCallback loginCallback) {

        RequestBody loginRequestBody = RequestBody.create(JSON,loginJson);
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
