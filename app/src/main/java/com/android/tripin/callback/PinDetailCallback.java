package com.android.tripin.callback;

/**
 * Create by kolos on 2018/6/17.
 * Description:
 */
public interface PinDetailCallback {
    /**
     * 修改Pin信息成功时
     */
    void onChangePinDetailSuccess();
    /**
     * 修改Pin信息失败时
     */
    void onChangePinDetailFailed();
    /**
     * 网络连接错误，修改失败时
     */
    void onConnectError();
    /**
     * 删除pin成功时
     */
    void onDeletePinSuccess();
    /**
     * 删除pin失败时
     */
    void onDeletePinFailed();
}
