package com.android.tripin.model.interfaces;

import com.android.tripin.callback.PinDetailCallback;

/**
 * Create by kolos on 2018/6/17.
 * Description:
 */
public interface IPinDetailModel {
    /**
     * 修改Pin内容
     */
    void changePinDetail(String changePinDetailJson, PinDetailCallback pinDetailCallback);
    /**
     * 删除当前pin
     */
    void deletePin(String deletePinJson,PinDetailCallback pinDetailCallback);
}
