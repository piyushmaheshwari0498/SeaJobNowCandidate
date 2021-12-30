package com.example.seajobnowcandidate.Entity.response;

import com.example.seajobnowcandidate.Entity.request.PostJobDetailsRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostJobDetailsResponse {

    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<PostJobDetailsRequest> data = null;

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

    public List<PostJobDetailsRequest> getData() {
        return data;
    }

    public void setData(List<PostJobDetailsRequest> data) {
        this.data = data;
    }
}


