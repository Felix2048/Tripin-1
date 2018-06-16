package com.android.tripin.fragment.map;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

/**
 * Created by Felix on 6/15/2018.
 * Description: 自定义的GeoCoderResult监听
 */
public class MyOnGetGeoCoderResultListener implements OnGetGeoCoderResultListener {

    private MapFragment mapFragment;

    public MyOnGetGeoCoderResultListener(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    // 反地理编码查询结果回调函数
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            // 没有检测到结果
            mapFragment.mapFragmentAuxiliary.showToast("抱歉，未能找到结果");
        }
        else {
//            mapFragment.mapFragmentAuxiliary.showToast("位置：" + result.getAddress());
            mapFragment.mapCenterAddress = result.getAddress();
            mapFragment.cityInMap = result.getAddressDetail().city;
            mapFragment.mapFragmentAuxiliary.addressComponent = result.getAddressDetail();
        }
    }
    // 地理编码查询结果回调函数
    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            // 没有检测到结果
            mapFragment. mapFragmentAuxiliary.showToast("抱歉，未能找到结果");
        }
        else {
//            mapFragment.mapFragmentAuxiliary.showToast("位置：" + result.getLocation().toString());
        }
    }
};
