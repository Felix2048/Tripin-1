package com.android.tripin.util;

import android.util.Log;

import com.android.tripin.entity.ResponseMessage;
import com.android.tripin.entity.UserInfo;
import com.android.tripin.entity.VerificationCode;
import com.google.gson.Gson;

public class ParserJsonToDataUtil {
    private final static String TAG = ParserJsonToDataUtil.class.getSimpleName();

    private static Gson gson = new Gson();

    public static String getLoginResponseMessage(String loginResponse){
        ResponseMessage responseMessage = gson.fromJson(loginResponse,ResponseMessage.class);
        String errorCode = responseMessage.getErrorCode();
        return errorCode;
    }

    public static UserInfo getLoginDataResponseMessage(String loginResponse) {
        ResponseMessage responseMessage = gson.fromJson(loginResponse,ResponseMessage.class);
        UserInfo loginData = (UserInfo) responseMessage.getData();
        return loginData;
    }

    public static String getSignUpResponseMessage(String signUpResponse) {
        ResponseMessage responseMessage = gson.fromJson(signUpResponse,ResponseMessage.class);
        String errorCode = responseMessage.getErrorCode();
        return errorCode;
    }

    public static String getSendVerificationCodeResponseMessage(String sendVerificationCodeUpResponse) {
        VerificationCode getverificationCode = gson.fromJson(sendVerificationCodeUpResponse,VerificationCode.class);
        String verificationCode = getverificationCode.getVerificationCode();
        return verificationCode;
    }

    public static String  getChangePersonalFileResponseMessage(String changePersonalFileResponse) {
        ResponseMessage responseMessage = gson.fromJson(changePersonalFileResponse,ResponseMessage.class);
        String errorCode = responseMessage.getErrorCode();
        return errorCode;
    }


    /**
     * 回调结果处理只需取得errorCode,故统一使用此方法，以上方法仍保留，暂时没时间去该代码。。。。
     * @param requestResponse
     * @return
     */
    public static String getResponseErrorCode(String requestResponse) {
        ResponseMessage responseMessage = gson.fromJson(requestResponse,ResponseMessage.class);
        String errorCode = responseMessage.getErrorCode();
        return errorCode;
    }
}
