package com.android.tripin.entity;

import com.android.tripin.enums.PinStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Felix on 6/11/2018.
 * Description:
 */
public class Pin implements Serializable {
    public final static String TAG = Pin.class.getSimpleName();

    private static final long serialVersionUID = 1L;

    private int pinID;

    private int planID;

    private double pinLatitude;

    private double pinLongitude;

    private String pinTitle;

    private Date pinArrival;

    private Date pinDeparture;

    private PinStatus pinStatus;

    private String pinNotes;

}
