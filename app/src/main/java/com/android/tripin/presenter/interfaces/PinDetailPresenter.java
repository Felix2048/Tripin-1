package com.android.tripin.presenter.interfaces;

import com.android.tripin.activity.PinDetailActivity;
import com.android.tripin.callback.PinDetailCallback;
import com.android.tripin.model.PinDetailModel;

/**
 * Create by kolos on 2018/6/17.
 * Description:
 */
public class PinDetailPresenter implements IPinDetailPresenter {

    private final static String TAG = PinDetailPresenter.class.getSimpleName();
    private PinDetailActivity pinDetailActivity;
    private PinDetailModel pinDetailModel;
    private String changePinDetailJson;
    private String deletePinJson;

    public PinDetailPresenter(PinDetailActivity pinDetailActivity, PinDetailModel pinDetailModel) {
        this.pinDetailActivity = pinDetailActivity;
        this.pinDetailModel = pinDetailModel;
        /**
         * TODO
         * 获取将Pin详情，将其妆化成JSON数据，在这里进行初始化
         */
    }

    @Override
    public void changePinDetail() {
        pinDetailModel.changePinDetail(changePinDetailJson, new PinDetailCallback() {
            @Override
            public void onChangePinDetailSuccess() {
                pinDetailActivity.showChangeSuccess();
            }

            @Override
            public void onChangePinDetailFailed() {
                pinDetailActivity.showChangeFailed();
            }

            @Override
            public void onConnectError() {
                pinDetailActivity.showNetworkError();
            }

            @Override
            public void onDeletePinSuccess() {
                pinDetailActivity.showDeleteSuccess();
            }

            @Override
            public void onDeletePinFailed() {
                pinDetailActivity.showDeleteFailed();
            }
        });
    }

    @Override
    public void deletePin() {
            pinDetailModel.deletePin(deletePinJson, new PinDetailCallback() {
                @Override
                public void onChangePinDetailSuccess() {
                    pinDetailActivity.showChangeSuccess();
                }

                @Override
                public void onChangePinDetailFailed() {
                    pinDetailActivity.showChangeFailed();
                }

                @Override
                public void onConnectError() {
                    pinDetailActivity.showNetworkError();
                }

                @Override
                public void onDeletePinSuccess() {
                    pinDetailActivity.showDeleteSuccess();
                }

                @Override
                public void onDeletePinFailed() {
                    pinDetailActivity.showDeleteFailed();
                }
            });
    }
}
