package com.android.tripin.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.tripin.MainActivity;
import com.android.tripin.R;
import com.android.tripin.base.BaseFragment;
import com.android.tripin.callback.SearchCallback;
import com.android.tripin.entity.UserInfo;
import com.android.tripin.util.OkHttp3Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.android.tripin.util.ChangeDataToJsonUtil.parseSearchInfoToJson;
import static com.android.tripin.util.ParserJsonToDataUtil.getLoginResponseMessage;
import static com.android.tripin.util.ParserJsonToDataUtil.getResponseErrorCode;

/**
 * Created by Felix on 6/8/2018.
 * Description:
 */

public class InvitationFragment extends BaseFragment implements SearchCallback{

    private final static String TAG = InvitationFragment.class.getSimpleName();
    public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
    public OkHttpClient client = OkHttp3Util.getClient();

    private final static String TITLE = "Share";
    private EditText searchWithUserNameOrPhone;
    private Button btnSearch;
    private RecyclerView searchListRecyclerView;
    private RecyclerView searchHistoryListRecyclerView;
    private List<UserInfo> userInfoList = new ArrayList<>();
    public static String getTitle() {
        return TITLE;
    }
    private static InvitationFragment invitationFragment = null;

    public static InvitationFragment newInstance() {
        if (null != invitationFragment) {
            return invitationFragment;
        }
        else {
            invitationFragment = new InvitationFragment();
            return invitationFragment;
        }
    }
    /**
     * onCreateView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_invitation,container,false);
        searchWithUserNameOrPhone = (EditText)v.findViewById(R.id.search_user_name_or_phone);
        btnSearch = (Button) v.findViewById(R.id.btn_search_user);
        searchListRecyclerView = (RecyclerView) v.findViewById(R.id.search_result_list);
        searchHistoryListRecyclerView = (RecyclerView) v.findViewById(R.id.search_history_list);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = searchWithUserNameOrPhone.getText().toString().trim();
                //todo:将info封装成json，发送
                String searchJson = parseSearchInfoToJson(info);
                RequestBody requestBody = RequestBody.create(JSON,searchJson);
                Request searchRequest = new Request.Builder()
                        .url("")
                        .post(requestBody)
                        .build();
                client.newCall(searchRequest).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        onSearchFailed();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String searchResponse = response.body().toString();
                        String errorCode = getResponseErrorCode(searchResponse);
                        switch (errorCode){
                           //todo 处理搜索结果返回逻辑
                            case "0000" :
                                onSearchSuccess();
                            case "400" :
                                onSearchFailed();
                            default:
                                break;
                        }
                    }
                });
            }
        });
        return v;
    }

    @Override
    public void onSearchSuccess() {
        //todo 将搜索返回的信息同时更新到两个textView中
    }

    @Override
    public void onSearchFailed() {
        Toast.makeText(this.getContext(),"该用户不存在或网络错误",Toast.LENGTH_SHORT).show();
    }
}