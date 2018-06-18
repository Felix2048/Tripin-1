package com.android.tripin.fragment.schedule;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tripin.R;
import com.android.tripin.base.BaseFragment;
import com.android.tripin.entity.Pin;
import com.android.tripin.manager.DataManager;

import java.util.Collections;
import java.util.List;

/**
 * Created by Felix on 6/8/2018.
 * Description:
 */

public class ScheduleFragment extends BaseFragment {

    private final static String TAG = ScheduleFragment.class.getSimpleName();

    private final static String TITLE = "Plan";

    public static String getTitle() {
        return TITLE;
    }

    private RecyclerView recyclerView;
    private ScheduleRecycleViewAdapter scheduleRecycleViewAdapter;
    private List<Pin> pinList;

    /**
     * onCreateView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule,container,false);
        pinList = DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinList();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        scheduleRecycleViewAdapter = new ScheduleRecycleViewAdapter(pinList);
        recyclerView.setAdapter(scheduleRecycleViewAdapter);
        //为RecycleView绑定触摸事件
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //首先回调的方法 返回int表示是否监听该方向
                int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;//拖拽
                int swipeFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;//侧滑删除
                return makeMovementFlags(dragFlags,swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //滑动事件
                Collections.swap(pinList,viewHolder.getAdapterPosition(),target.getAdapterPosition());
                scheduleRecycleViewAdapter.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //侧滑事件
                pinList.remove(viewHolder.getAdapterPosition());
                scheduleRecycleViewAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }

            @Override
            public boolean isLongPressDragEnabled() {
                //是否可拖拽
                return true;
            }
        });
        helper.attachToRecyclerView(recyclerView);
        return view;
    }

}