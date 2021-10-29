package com.example.seajobnowcandidate.Entity.response;

import com.example.seajobnowcandidate.Entity.request.LoginRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {
    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("StatusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("data")
    @Expose
    private List<LoginRequest> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

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

    public List<LoginRequest> getData() {
        return data;
    }

    public void setData(List<LoginRequest> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
