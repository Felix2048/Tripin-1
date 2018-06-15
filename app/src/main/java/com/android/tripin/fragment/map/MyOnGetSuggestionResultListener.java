package com.android.tripin.fragment.map;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;

/**
 * Created by Felix on 6/15/2018.
 * Description: 自定义的搜索建议监听
 */
public class MyOnGetSuggestionResultListener implements OnGetSuggestionResultListener {

    private MapFragment mapFragment;

    public MyOnGetSuggestionResultListener(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
        if (suggestionResult == null || suggestionResult.error != SearchResult.ERRORNO.NO_ERROR) {
            //  未找到相关结果
        }
        else {
            //  获取在线建议检索结果
        }
    }
}
