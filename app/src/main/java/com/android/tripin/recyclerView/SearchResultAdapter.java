package com.android.tripin.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tripin.R;
import com.android.tripin.entity.UserInfo;

import java.util.List;

/**
 * Create by kolos on 2018/6/18.
 * Description:
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private List<UserInfo> mUserInfoList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName ;
        TextView phone;
        TextView email;
         public ViewHolder(View v) {
             super(v);
             userName = (TextView) v.findViewById(R.id.result_user_name);
             phone = (TextView) v.findViewById(R.id.result_phone);
             email = (TextView) v.findViewById(R.id.result_email);
         }
    }

    public SearchResultAdapter(List<UserInfo> userInfoList) {
        mUserInfoList = userInfoList;
    }
    @NonNull
    @Override
    public SearchResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_info_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter.ViewHolder holder, int position) {
        UserInfo userInfo = mUserInfoList.get(position);
        holder.userName.setText(userInfo.getUserName());
        holder.phone.setText(userInfo.getPhone());
        holder.email.setText(userInfo.getEmail());
    }

    @Override
    public int getItemCount() {
        return mUserInfoList.size();
    }
}
