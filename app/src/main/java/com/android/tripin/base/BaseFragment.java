package com.android.tripin.base;

import com.android.tripin.base.arch.QMUIFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

/**
 * Created by Felix on 6/8/2018.
 * Description: Fragment 基类
 */

public abstract class BaseFragment extends QMUIFragment {

    public BaseFragment() {
    }

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(getContext(), 100);
    }

}
