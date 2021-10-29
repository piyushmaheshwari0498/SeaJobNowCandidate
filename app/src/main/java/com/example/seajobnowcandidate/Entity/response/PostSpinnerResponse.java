package com.example.seajobnowcandidate.Entity.response;

import com.example.seajobnowcandidate.Entity.request.PostSpinnerDataRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostSpinnerResponse {
    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("StatusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("data")
    @Expose
    private PostSpinnerDataRequest data;

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

    public PostSpinnerDataRequest getData() {
        return data;
    }

    public void setData(PostSpinnerDataRequest data) {
        this.data = data;
    }

}
