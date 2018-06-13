package com.android.tripin.entity;

import com.android.tripin.enums.PinStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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

    public Pin() {
    }

    public Pin(int planID, double pinLatitude, double pinLongitude, String pinTitle, Date pinArrival, Date pinDeparture, PinStatus pinStatus, String pinNotes) {
        this.planID = planID;
        this.pinLatitude = pinLatitude;
        this.pinLongitude = pinLongitude;
        this.pinTitle = pinTitle;
        this.pinArrival = pinArrival;
        this.pinDeparture = pinDeparture;
        this.pinStatus = pinStatus;
        this.pinNotes = pinNotes;
    }

    public Pin(int pinID, int planID, double pinLatitude, double pinLongitude, String pinTitle, Date pinArrival, Date pinDeparture, PinStatus pinStatus, String pinNotes) {
        this.pinID = pinID;
        this.planID = planID;
        this.pinLatitude = pinLatitude;
        this.pinLongitude = pinLongitude;
        this.pinTitle = pinTitle;
        this.pinArrival = pinArrival;
        this.pinDeparture = pinDeparture;
        this.pinStatus = pinStatus;
        this.pinNotes = pinNotes;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getPinID() {
        return pinID;
    }

    public void setPinID(int pinID) {
        this.pinID = pinID;
    }

    public int getPlanID() {
        return planID;
    }

    public void setPlanID(int planID) {
        this.planID = planID;
    }

    public double getPinLatitude() {
        return pinLatitude;
    }

    public void setPinLatitude(double pinLatitude) {
        this.pinLatitude = pinLatitude;
    }

    public double getPinLongitude() {
        return pinLongitude;
    }

    public void setPinLongitude(double pinLongitude) {
        this.pinLongitude = pinLongitude;
    }

    public String getPinTitle() {
        return pinTitle;
    }

    public void setPinTitle(String pinTitle) {
        this.pinTitle = pinTitle;
    }

    public Date getPinArrival() {
        return pinArrival;
    }

    public void setPinArrival(Date pinArrival) {
        this.pinArrival = pinArrival;
    }

    public Date getPinDeparture() {
        return pinDeparture;
    }

    public void setPinDeparture(Date pinDeparture) {
        this.pinDeparture = pinDeparture;
    }

    public PinStatus getPinStatus() {
        return pinStatus;
    }

    public void setPinStatus(PinStatus pinStatus) {
        this.pinStatus = pinStatus;
    }

    public String getPinNotes() {
        return pinNotes;
    }

    public void setPinNotes(String pinNotes) {
        this.pinNotes = pinNotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pin pin = (Pin) o;
        return getPinID() == pin.getPinID() &&
                getPlanID() == pin.getPlanID() &&
                Double.compare(pin.getPinLatitude(), getPinLatitude()) == 0 &&
                Double.compare(pin.getPinLongitude(), getPinLongitude()) == 0 &&
                Objects.equals(getPinTitle(), pin.getPinTitle()) &&
                Objects.equals(getPinArrival(), pin.getPinArrival()) &&
                Objects.equals(getPinDeparture(), pin.getPinDeparture()) &&
                getPinStatus() == pin.getPinStatus() &&
                Objects.equals(getPinNotes(), pin.getPinNotes());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPinID(), getPlanID(), getPinLatitude(), getPinLongitude(), getPinTitle(), getPinArrival(), getPinDeparture(), getPinStatus(), getPinNotes());
    }
}
