package com.example.seajobnowcandidate.Entity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullExpRequest {

    @SerializedName("ship_name")
    @Expose
    private String shipName;
    @SerializedName("vessel_type")
    @Expose
    private String vesselType;
    @SerializedName("engine_type")
    @Expose
    private String engineType;
    @SerializedName("engine_power")
    @Expose
    private String enginePower;
    @SerializedName("fromdate")
    @Expose
    private String fromdate;
    @SerializedName("to_date")
    @Expose
    private String toDate;
    @SerializedName("total_time")
    @Expose
    private String totalTime;
    @SerializedName("reason_stick")
    @Expose
    private String reasonStick;
    @SerializedName("change_reason")
    @Expose
    private String changeReason;
    @SerializedName("circumtance")
    @Expose
    private String circumtance;
    @SerializedName("signed_off")
    @Expose
    private String signedOff;
    @SerializedName("surgery")
    @Expose
    private String surgery;
    @SerializedName("achivement")
    @Expose
    private String achivement;

    public FullExpRequest(String ship_name, String vessel_type, String engine_type, String engine_power,
                          String fromdate, String to_date, String total_time,
                          String reason_stick, String change_reason, String circumtance,
                          String signed_off, String surgery, String achivement) {
        this.shipName = ship_name;
        this.vesselType = vessel_type;
        this.engineType = engine_type;
        this.enginePower = engine_power;
        this.fromdate = fromdate;
        this.toDate = to_date;
        this.totalTime = total_time;
        this.reasonStick = reason_stick;
        this.changeReason = change_reason;
        this.circumtance = circumtance;
        this.signedOff = signed_off;
        this.surgery = surgery;
        this.achivement = achivement;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getVesselType() {
        return vesselType;
    }

    public void setVesselType(String vesselType) {
        this.vesselType = vesselType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(String enginePower) {
        this.enginePower = enginePower;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getReasonStick() {
        return reasonStick;
    }

    public void setReasonStick(String reasonStick) {
        this.reasonStick = reasonStick;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public String getCircumtance() {
        return circumtance;
    }

    public void setCircumtance(String circumtance) {
        this.circumtance = circumtance;
    }

    public String getSignedOff() {
        return signedOff;
    }

    public void setSignedOff(String signedOff) {
        this.signedOff = signedOff;
    }

    public String getSurgery() {
        return surgery;
    }

    public void setSurgery(String surgery) {
        this.surgery = surgery;
    }

    public String getAchivement() {
        return achivement;
    }

    public void setAchivement(String achivement) {
        this.achivement = achivement;
    }


    @Override
    public String toString() {
        return "FullExpRequest{" +
                "shipName='" + shipName + '\'' +
                ", vesselType='" + vesselType + '\'' +
                ", engineType='" + engineType + '\'' +
                ", enginePower='" + enginePower + '\'' +
                ", fromdate='" + fromdate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", totalTime='" + totalTime + '\'' +
                ", reasonStick='" + reasonStick + '\'' +
                ", changeReason='" + changeReason + '\'' +
                ", circumtance='" + circumtance + '\'' +
                ", signedOff='" + signedOff + '\'' +
                ", surgery='" + surgery + '\'' +
                ", achivement='" + achivement + '\'' +
                '}';
    }
}
