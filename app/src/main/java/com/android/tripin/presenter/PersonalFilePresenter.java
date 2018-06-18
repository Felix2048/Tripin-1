package com.android.tripin.presenter;

import com.android.tripin.activity.LauncherActivity;
import com.android.tripin.activity.PersonalFileActivity;
import com.android.tripin.callback.PersonalFileCallback;
import com.android.tripin.model.PersonalFileModel;
import com.android.tripin.presenter.interfaces.IPersonalFilePresenter;
import com.android.tripin.util.ChangeDataToJsonUtil;

/**
 * Create by kolos on 2018/6/16.
 * Description:
 */
public class PersonalFilePresenter implements IPersonalFilePresenter {
    private final static String TAG = PersonalFilePresenter.class.getSimpleName();

    private PersonalFileModel personalFileModel;
    private PersonalFileActivity personalFileActivity;

    public PersonalFilePresenter(PersonalFileModel personalFileModel, PersonalFileActivity personalFileActivity) {
        this.personalFileModel = personalFileModel;
        this.personalFileActivity = personalFileActivity;
        changePersonalFileRequestJson = ChangeDataToJsonUtil.getChangePersonalFileRequestJson(personalFileActivity.getUserName(),personalFileActivity.getUserEmail(),personalFileActivity.getUserPhone());
    }

    /**
     * 获取用户名，密码，将数据转化成loginJson
     */
    String changePersonalFileRequestJson;



    @Override
    public void changePersonalFile() {
        personalFileModel.changePersonalFile(changePersonalFileRequestJson, new PersonalFileCallback() {
            @Override
            public void onChangeSuccess() {
                personalFileActivity.showChangeSuccess();
                personalFileActivity.setMyHint();
            }

            @Override
            public void onChangeFailed() {
                personalFileActivity.showChangeFailed();
            }

            @Override
            public void onConnectFailed() {
                personalFileActivity.showNetworkError();
            }

            @Override
            public void onLogoutSuccess() {
                personalFileActivity.showLogoutSuccess();
            }
        });
    }

    @Override
    public void logout() {
        /**
         * 写一个Intent，跳转到主界面
         */
        personalFileActivity.backToHome();
        LauncherActivity.CHECK_LOGIN_STATUS_FLAG=1;
        personalFileActivity.showLogoutSuccess();
    }
}
