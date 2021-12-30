package com.example.seajobnowcandidate.Entity.response;

import com.example.seajobnowcandidate.Entity.data.VesselTypeData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VesselResponse {
    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private VesselTypeData data;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public VesselTypeData getData() {
        return data;
    }

    public void setData(VesselTypeData data) {
        this.data = data;
    }
}
