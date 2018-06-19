package com.android.tripin.fragment.schedule;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tripin.R;
import com.android.tripin.entity.Pin;

import java.util.List;

/**
 * Created by Felix on 6/19/2018.
 * Description:
 */
public class ScheduleRecycleViewAdapter extends RecyclerView.Adapter<ScheduleRecycleViewAdapter.MyViewHolder> {

    private List<Pin> list;

    public ScheduleRecycleViewAdapter(List<Pin> list) {
        this.list = list;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView schedule_pin_title, schedule_pin_arrive_time, schedule_pin_departure_time;

        public MyViewHolder(View itemView) {
            super(itemView);
            schedule_pin_title = (TextView) itemView.findViewById(R.id.schedule_pin_title);
            schedule_pin_arrive_time = (TextView) itemView.findViewById(R.id.schedule_pin_arrive_time);
            schedule_pin_departure_time = (TextView) itemView.findViewById(R.id.schedule_pin_departure_time);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item, parent, false);
        MyViewHolder holder=  new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Pin pin = list.get(position);
        holder.schedule_pin_title.setText(pin.getPinTitle());
        holder.schedule_pin_arrive_time.setText(pin.getPinArrival().toString());
        holder.schedule_pin_departure_time.setText(pin.getPinDeparture().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}