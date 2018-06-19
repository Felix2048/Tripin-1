package com.android.tripin.recyclerView;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.tripin.R;
import com.android.tripin.entity.Pin;
import com.android.tripin.entity.Plan;
import com.android.tripin.enums.PlanType;
import com.android.tripin.enums.Transportation;
import com.android.tripin.manager.DataManager;

import java.util.Date;
import java.util.List;

/**
 * Create by kolos on 2018/6/18.
 * Description:
 */
public class CreatePlanAdapter extends RecyclerView.Adapter<CreatePlanAdapter.MyViewHolder>{
    private Context context;
    private List<Plan> list;

    public CreatePlanAdapter(Context context, List<Plan> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.plan_item, parent,
                false));
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv.setText(list.get(position).getPlanName());
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.setCurrentPlan(DataManager.getPlanList().get(DataManager.getPlanList().indexOf(list.get(position))));
                DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).setUpdated(true);
                Snackbar.make(v, "当前计划已修改为" + list.get(position).getPlanName(), Snackbar.LENGTH_SHORT).show();
            }
        });
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 1 || (list.get(position).equals(DataManager.getCurrentPlan()))) {
                    Snackbar.make(v, "此条目不能删除", Snackbar.LENGTH_SHORT).show();
                } else {
                    //               删除自带默认动画
                    removeData(position);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    //  添加数据
    public void addData(int position) {
//      在list中添加数据，并通知条目加入一条
        Plan plan =  new Plan(DataManager.getPlanCountAndIncrease(), DataManager.getMapCountAndIncrease(),"计划" + position,
                PlanType.PUBLIC, new Date(), new Date(), Transportation.MASS_TRANSIT);
//        list.add(position,plan);
        DataManager.createPlan(plan);

        list = DataManager.getPlanList();
        //添加动画
        notifyItemInserted(position);
    }
    //  删除数据
    public void removeData(int position) {
        if (DataManager.getCurrentPlan().equals(list.get(position))) {
            return;
        }
        list.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
    /**
     * ViewHolder的类，用于缓存控件
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv, tv_delete;
        //因为删除有可能会删除中间条目，然后会造成角标越界，所以必须整体刷新一下！
        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num);
            tv_delete = (TextView) view.findViewById(R.id.tv_delete);
        }
    }
}