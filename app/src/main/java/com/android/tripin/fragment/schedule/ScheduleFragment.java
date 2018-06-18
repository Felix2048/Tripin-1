package com.android.tripin.fragment.schedule;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tripin.R;
import com.android.tripin.base.BaseFragment;
import com.android.tripin.entity.Pin;
import com.android.tripin.manager.DataManager;

import java.util.List;

/**
 * Created by Felix on 6/8/2018.
 * Description:
 */

public class ScheduleFragment extends BaseFragment {

    private final static String TAG = ScheduleFragment.class.getSimpleName();

    private static ScheduleFragment scheduleFragment = null;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        scheduleRecycleViewAdapter = new ScheduleRecycleViewAdapter(pinList);
        recyclerView.setAdapter(scheduleRecycleViewAdapter);
        return view;
    }

    public static ScheduleFragment newInstance() {
       if (null != scheduleFragment) {
           return scheduleFragment;
       }
       else {
           scheduleFragment = new ScheduleFragment();
           return scheduleFragment;
       }
    }
}