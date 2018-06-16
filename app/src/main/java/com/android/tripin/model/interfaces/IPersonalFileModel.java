package com.android.tripin.model.interfaces;

import com.android.tripin.callback.PersonalFileCallback;

/**
 * Create by kolos on 2018/6/16.
 * Description:
 */
public interface IPersonalFileModel {
    /**
     * 更改个人信息
     */
    void changePersonalFile(String changePersonalFileJson, PersonalFileCallback personalFileCallback);

}
