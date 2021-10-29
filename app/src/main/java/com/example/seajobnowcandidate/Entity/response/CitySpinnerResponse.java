package com.example.seajobnowcandidate.Entity.response;

import com.example.seajobnowcandidate.Entity.request.SpinnerData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CitySpinnerResponse {
    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("StatusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("data")
    @Expose
    private SpinnerData data;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public SpinnerData getData() {
        return data;
    }

    public void setData(SpinnerData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
