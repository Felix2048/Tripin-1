package com.android.tripin.model;

import com.android.tripin.callback.PinDetailCallback;
import com.android.tripin.model.interfaces.IPinDetailModel;
import com.android.tripin.util.OkHttp3Util;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.android.tripin.util.ParserJsonToDataUtil.getResponseErrorCode;

/**
 * Create by kolos on 2018/6/17.
 * Description:
 */
public class PinDetailModel implements IPinDetailModel {

    private final static String TAG = PinDetailModel.class.getSimpleName();
    public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
    public OkHttpClient client = OkHttp3Util.getClient();


    @Override
    public void changePinDetail(String changePinDetailJson, PinDetailCallback pinDetailCallback) {
        RequestBody changePinDetailRequestBody = RequestBody.create(JSON,changePinDetailJson);
        Request changePinDetailRequest = new Request.Builder()
                                        .url("")
                                        .post(changePinDetailRequestBody)
                                        .build();
        client.newCall(changePinDetailRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                pinDetailCallback.onConnectError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String changePinDetailResponse = response.body().string();
                String errorCode = getResponseErrorCode(changePinDetailResponse);
                switch (errorCode){
                    case "0000" :
                        pinDetailCallback.onChangePinDetailSuccess();
                        break;
                    case "400" :
                        pinDetailCallback.onChangePinDetailFailed();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void deletePin(String deletePinJson,PinDetailCallback pinDetailCallback) {
        RequestBody deletePinRequestBody = RequestBody.create(JSON,deletePinJson);
        Request deletePinRequest = new Request.Builder()
                .url("")
                .post(deletePinRequestBody)
                .build();
        client.newCall(deletePinRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                pinDetailCallback.onConnectError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String deletePinDetailResponse = response.body().string();
                String errorCode = getResponseErrorCode(deletePinDetailResponse);
                switch (errorCode){
                    case "0000" :
                        pinDetailCallback.onDeletePinSuccess();
                        break;
                    case "400" :
                        pinDetailCallback.onDeletePinFailed();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
