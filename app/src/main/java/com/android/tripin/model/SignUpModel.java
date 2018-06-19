package com.android.tripin.model;

import android.content.SharedPreferences;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.android.tripin.callback.SignUpCallback;
import com.android.tripin.manager.DataManager;
import com.android.tripin.model.interfaces.ISignUpModel;
import com.android.tripin.util.OkHttp3Util;
import com.android.tripin.util.ParserJsonToDataUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.android.tripin.model.LoginModel.JSON;
import static com.android.tripin.util.ParserJsonToDataUtil.getLoginResponseMessage;
import static com.android.tripin.util.ParserJsonToDataUtil.getSignUpResponseMessage;


public class SignUpModel implements ISignUpModel {

    DataManager dataManager = new DataManager();
    public OkHttpClient client = OkHttp3Util.getClient();

    /**
     * 发送注册网络请求，获取反馈信息
     * @param signUpJson
     * @param signUpCallback
     */
    @Override
    public void signUp(String signUpJson,SignUpCallback signUpCallback) {

        /**
         * 请求地址尚未指定
         */

//        RequestBody signUpRequestBody = RequestBody.create(JSON,signUpJson);
//        Request signUpRequest = new Request.Builder()
//                .url("")
//                .post(signUpRequestBody)
//                .build();
//        client.newCall(signUpRequest).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                signUpCallback.onConnectFailed();
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String signUpResponse = response.body().string();
//                String errorCode = getSignUpResponseMessage(signUpResponse);
//                switch (errorCode){
//                    case "0000" :
//                        signUpCallback.onSuccess();
//                        break;
//                    case "1002" :
//                        signUpCallback.onUserNameUsed();
//                        break;
//                    case "1003":
//                        signUpCallback.onPhoneUsed();
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
        signUpCallback.onSuccess();
    }

    /**
     * 发送获取验证码请求，取得验证码存储至本地
     * @param
     */
    @Override
    public void sendVerificationCode(String phone) {

        /*
         * 请求地址尚未指定
        */

        new Thread(new Runnable() {
            @Override
            public void run() {
                String validateCode = (int) (Math.random() * 10000) + "";
                if (validateCode.length() < 4)
                    for (int i = 0; i < 4 - validateCode.length(); i++)
                        validateCode = "0" + validateCode;
                DataManager.setVerificationCode(validateCode);
                System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
                System.setProperty("sun.net.client.defaultReadTimeout", "10000");
                final String product = "Dysmsapi";
                final String domain = "dysmsapi.aliyuncs.com";
                final String accessKeyId = "LTAI5uqu2psqSOaP";
                final String accessKeySecret = "WtskXmINTnxlG2ZP078n1OUJ4EYYBN";
                IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                        accessKeySecret);
                try {
                    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
                } catch (ClientException e) {
                    e.printStackTrace();
                }

                IAcsClient acsClient = new DefaultAcsClient(profile);
                SendSmsRequest request = new SendSmsRequest();
                request.setMethod(MethodType.POST);
                request.setPhoneNumbers(phone);
                request.setSignName("Tripin");
                request.setTemplateCode("SMS_137665892");
                request.setTemplateParam("{\"code\":\"" + validateCode + "\"}");
                request.setOutId("yourOutId");
                SendSmsResponse sendSmsResponse = null;

                try {
                    sendSmsResponse = acsClient.getAcsResponse(request);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
