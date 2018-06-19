package com.android.tripin.entity;

import android.widget.TextView;

/**
 * Create by kolos on 2018/6/19.
 * Description:
 */
public class PlanItem {

    public TextView getPlanName() {
        return planName;
    }

    public void setPlanName(TextView planName) {
        this.planName = planName;
    }

    public PlanItem(TextView planName) {
        this.planName = planName;
    }

    private TextView planName;

}
