package com.example.seajobnowcandidate.Entity.response;

import com.example.seajobnowcandidate.Entity.request.CandidateDetailsRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CandidateDetailsResponse {

    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<CandidateDetailsRequest> data = null;

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

    public List<CandidateDetailsRequest> getData() {
        return data;
    }

    public void setData(List<CandidateDetailsRequest> data) {
        this.data = data;
    }

}
