package com.example.seajobnowcandidate.Model;

public class SavedJob {
    String designation,companyName,address,daySaved;

    public SavedJob(String designation, String companyName, String address, String daySaved) {
        this.designation = designation;
        this.companyName = companyName;
        this.address = address;
        this.daySaved = daySaved;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDaySaved() {
        return daySaved;
    }

    public void setDaySaved(String daySaved) {
        this.daySaved = daySaved;
    }
}
