package com.android.tripin.view;

/**
 * Create by kolos on 2018/6/17.
 * Description:
 */
public interface IPinDetailView {
    /**
     * 显示修改成功信息
     */
    void showChangeSuccess();
    /**
     * 显示修改失败提示信息
     */
    void showChangeFailed();
    /**
     * 显示删除成功信息
     */
    void showDeleteSuccess();
    /**
     * 显示删除失败信息
     */
    void showDeleteFailed();
    /**
     * 显示网络错误
     */
    void showNetworkError();
    /**
     * 获取Pin相关信息
     */
    String getArriveTime();
    String getLeaveTime();
    String getPinNotes();
}
