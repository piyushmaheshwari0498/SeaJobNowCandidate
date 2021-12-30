package com.example.seajobnowcandidate.Entity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VesselRequest {
    @SerializedName("vt_id")
    @Expose
    private String vtId;
    @SerializedName("vt_name")
    @Expose
    private String vtName;
    @SerializedName("vt_img")
    @Expose
    private String vtImg;

    public String getVtId() {
        return vtId;
    }

    public void setVtId(String vtId) {
        this.vtId = vtId;
    }

    public String getVtName() {
        return vtName;
    }

    public void setVtName(String vtName) {
        this.vtName = vtName;
    }

    public String getVtImg() {
        return vtImg;
    }

    public void setVtImg(String vtImg) {
        this.vtImg = vtImg;
    }


}
