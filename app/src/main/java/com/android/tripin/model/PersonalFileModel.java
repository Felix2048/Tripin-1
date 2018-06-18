package com.android.tripin.model;

import com.android.tripin.callback.PersonalFileCallback;
import com.android.tripin.model.interfaces.IPersonalFileModel;
import com.android.tripin.util.OkHttp3Util;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.android.tripin.util.ParserJsonToDataUtil.getChangePersonalFileResponseMessage;
import static com.android.tripin.util.ParserJsonToDataUtil.getLoginResponseMessage;

/**
 * Create by kolos on 2018/6/16.
 * Description:
 */
public class PersonalFileModel implements IPersonalFileModel {
    private final static String TAG = PersonalFileModel.class.getSimpleName();

    public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
    public OkHttpClient client = OkHttp3Util.getClient();

    @Override
    public void changePersonalFile(String changePersonalFileJson, PersonalFileCallback personalFileCallback) {
        RequestBody changePersonalFileRequestBody = RequestBody.create(JSON,changePersonalFileJson);

        /**
         * todo
         * 增加修改个人信息界面所需url
         */
        Request changePersonalFileRequest = new Request.Builder()
                .url("")
                .post(changePersonalFileRequestBody)
                .build();
        client.newCall(changePersonalFileRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                personalFileCallback.onConnectFailed();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String changePersonalFileResponse = response.body().string();
                String errorCode = getChangePersonalFileResponseMessage(changePersonalFileResponse);
                switch (errorCode){
                    case "0" :
                        personalFileCallback.onChangeSuccess();
                        break;
                    case "400" :
                        personalFileCallback.onChangeFailed();
                        break;
                    default:
                        break;
                }
            }
        });

    }
}
