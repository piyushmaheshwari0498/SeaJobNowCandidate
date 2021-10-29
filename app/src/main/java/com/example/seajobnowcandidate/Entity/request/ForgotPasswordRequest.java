package com.example.seajobnowcandidate.Entity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordRequest {
    @SerializedName("API_ACCESS_KEY")
    @Expose
    private String apiAccessKey;
    @SerializedName("sc_indos")
    @Expose
    private String scIndos;

    public String getApiAccessKey() {
        return apiAccessKey;
    }

    public void setApiAccessKey(String apiAccessKey) {
        this.apiAccessKey = apiAccessKey;
    }

    public String getScIndos() {
        return scIndos;
    }

    public void setScIndos(String scIndos) {
        this.scIndos = scIndos;
    }

}
