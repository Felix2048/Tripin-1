package com.android.tripin.fragment.map;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;

/**
 * Created by Felix on 6/15/2018.
 * Description: 自定义的poi搜索结果监听
 */
public class MyOnGetPoiSearchResultListener implements OnGetPoiSearchResultListener {

    MapFragment mapFragment;

    public MyOnGetPoiSearchResultListener(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR) {
            //  未找到相关结果
        }
        else {
            //  获取在线建议检索结果
            if (mapFragment.isAddingPin) {  //  如果正在添加Pin，在地图上显示附近的poi搜索的result
                mapFragment.mapFragmentAuxiliary.showPoiInfo(poiResult);
            }
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }
}