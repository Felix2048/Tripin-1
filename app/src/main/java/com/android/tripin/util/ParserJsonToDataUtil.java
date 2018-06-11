package com.android.tripin.util;

import com.android.tripin.entity.ResponseMessage;
import com.google.gson.Gson;

public class ParserJsonToDataUtil {

    static Gson gson = new Gson();


    public static String getLoginResponseMessage(String loginResponse){
        ResponseMessage responseMessage = gson.fromJson(loginResponse,ResponseMessage.class);
        String errorCode = responseMessage.getErrorCode();
        return errorCode;
    }
}
