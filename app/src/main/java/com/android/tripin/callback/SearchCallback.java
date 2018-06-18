package com.android.tripin.callback;

/**
 * Create by kolos on 2018/6/18.
 * Description:
 */
public interface SearchCallback {
    /**
     * 搜索成功，用户存在
     */
    void onSearchSuccess();
    /**
     * 搜索失败，用户不存在
     */
    void onSearchFailed();
}
