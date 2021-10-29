package com.example.seajobnowcandidate.Entity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfirmPwdRequest {
    @SerializedName("API_ACCESS_KEY")
    @Expose
    private String apiAccessKey;
    @SerializedName("sc_pemail")
    @Expose
    private String scPemail;
    @SerializedName("sc_new_password")
    @Expose
    private String scNewPassword;
    @SerializedName("sc_confirm_password")
    @Expose
    private String scConfirmPassword;

    public String getApiAccessKey() {
        return apiAccessKey;
    }

    public void setApiAccessKey(String apiAccessKey) {
        this.apiAccessKey = apiAccessKey;
    }

    public String getScPemail() {
        return scPemail;
    }

    public void setScPemail(String scPemail) {
        this.scPemail = scPemail;
    }

    public String getScNewPassword() {
        return scNewPassword;
    }

    public void setScNewPassword(String scNewPassword) {
        this.scNewPassword = scNewPassword;
    }

    public String getScConfirmPassword() {
        return scConfirmPassword;
    }

    public void setScConfirmPassword(String scConfirmPassword) {
        this.scConfirmPassword = scConfirmPassword;
    }
}
