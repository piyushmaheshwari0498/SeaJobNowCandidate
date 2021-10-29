package com.example.seajobnowcandidate.Entity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("API_ACCESS_KEY")
    @Expose
    private String apiAccessKey;
    @SerializedName("sc_username")
    @Expose
    private String scUsername;
    @SerializedName("sc_password")
    @Expose
    private String scPassword;
    @SerializedName("sc_cand_login_flag")
    @Expose
    private String scCandLoginFlag;
    @SerializedName("sc_id")
    @Expose
    private String scId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("middle_name")
    @Expose
    private String middleName;
    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("acceess_key")
    @Expose
    private String acceessKey;

    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAcceessKey() {
        return acceessKey;
    }

    public void setAcceessKey(String acceessKey) {
        this.acceessKey = acceessKey;
    }
    public String getApiAccessKey() {
        return apiAccessKey;
    }

    public void setApiAccessKey(String apiAccessKey) {
        this.apiAccessKey = apiAccessKey;
    }

    public String getScUsername() {
        return scUsername;
    }

    public void setScUsername(String scUsername) {
        this.scUsername = scUsername;
    }

    public String getScPassword() {
        return scPassword;
    }

    public void setScPassword(String scPassword) {
        this.scPassword = scPassword;
    }

    public String getScCandLoginFlag() {
        return scCandLoginFlag;
    }

    public void setScCandLoginFlag(String scCandLoginFlag) {
        this.scCandLoginFlag = scCandLoginFlag;
    }

}
