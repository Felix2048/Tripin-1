package com.android.tripin.util;
import okhttp3.OkHttpClient;

/**
 * 使用单例设计模式，获取OkHttpClient实例，避免资源浪费
 */

public class OkHttp3Util {
    private final static String TAG = OkHttp3Util.class.getSimpleName();
    private static OkHttpClient client = new OkHttpClient();
    public static OkHttpClient getClient() {
        return client;
    }
}
