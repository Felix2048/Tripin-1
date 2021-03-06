package com.android.tripin.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.tripin.MainActivity;
import com.android.tripin.R;
import com.android.tripin.base.BaseActivity;
import com.android.tripin.entity.Plan;
import com.android.tripin.manager.DataManager;
import com.android.tripin.recyclerView.CreatePlanAdapter;
import com.android.tripin.util.ChangePlanNameDialogUtil;
import com.android.tripin.util.RecylerViewClickListener2;


import java.util.ArrayList;
import java.util.List;

public class CreatePlanActivity extends BaseActivity {

    private final static String TAG = CreatePlanActivity.class.getSimpleName();

    @Override
    protected int getContextViewId() {
        return R.layout.activity_create_plan;
    }


    private RecyclerView mRecyclerView;
    private ImageView iv_add;
    private CreatePlanAdapter adapter;
    private List<Plan> list = DataManager.getPlanList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);
        initView();
        initRecycle();
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //              添加自带默认动画
                adapter.addData(list.size());
            }
        });
    }
    private void initRecycle() {
        //  纵向滑动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
//      获取数据，向适配器传数据，绑定适配器
        adapter = new CreatePlanAdapter(CreatePlanActivity.this, list);
        mRecyclerView.setAdapter(adapter);

//      添加动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    private void initView() {
        iv_add = (ImageView) findViewById(R.id.iv_add);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    }

}
