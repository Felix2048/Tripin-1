package com.android.tripin.util;

import com.android.tripin.entity.ResponseMessage;
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
}
