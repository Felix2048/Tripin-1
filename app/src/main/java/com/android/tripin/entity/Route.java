package com.android.tripin.entity;

import com.android.tripin.enums.Transportation;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Felix on 6/15/2018.
 * Description: Route路线表的实例
 */
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    private int routeID;

    private int planID;

    private int origin;

    private int destination;

    private Transportation routeTransportation;

    private int routeTime;

    private boolean routeIsChosen;

    public Route() {
    }

}
