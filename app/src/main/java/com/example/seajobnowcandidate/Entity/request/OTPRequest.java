package com.example.seajobnowcandidate.Entity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTPRequest {
    @SerializedName("API_ACCESS_KEY")
    @Expose
    private String apiAccessKey;
    @SerializedName("otp")
    @Expose
    private String otp;

    public String getApiAccessKey() {
        return apiAccessKey;
    }

    public void setApiAccessKey(String apiAccessKey) {
        this.apiAccessKey = apiAccessKey;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
