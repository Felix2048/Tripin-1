package com.android.tripin.view;

import com.android.tripin.entity.Pin;

/**
 * Created by Felix on 6/11/2018.
 * Description:
 */
public interface IMapView {

    /**
     * 显示当前Plan下的所有pin和route
     */
    void showTrip();

    /**
     * 向地图中添加一个Pin
     * @param pin 需要添加的Pin
     */
    void addPin(Pin pin);

    /**
     * 将地图中的Pin移除
     */
    void removePin();

    /**
     * 将当前地图中的所有点移除
     */
    void removeAllPins();

    /**
     * 编辑地图中的pin
     */
    void editPin();

    /**
     * 返回到当前位置
     */
    void getMyLocation();
}
