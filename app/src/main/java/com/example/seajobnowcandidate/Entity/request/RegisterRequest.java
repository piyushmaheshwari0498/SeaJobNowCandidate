package com.example.seajobnowcandidate.Entity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterRequest {
    @SerializedName("API_ACCESS_KEY")
    @Expose
    private String apiAccessKey;
    @SerializedName("indos_no")
    @Expose
    private String indosNo;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("middle_name")
    @Expose
    private String middleName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("confirm_password")
    @Expose
    private String confirmPassword;
    @SerializedName("sc_rank")
    @Expose
    private String rankappliedfor;

    @SerializedName("sc_cand_register_flag")
    @Expose
    private String scCandRegisterFlag;
    @SerializedName("sailing_date")
    @Expose
    private String sailingDate;
    @SerializedName("leave_dur")
    @Expose
    private String leaveDur;
    @SerializedName("cdiscontinuety")
    @Expose
    private String cdiscontinuety;
    @SerializedName("creason")
    @Expose
    private String creason;
    @SerializedName("ship_type_worked")
    @Expose
    private String shipTypeWorked;
    @SerializedName("flex_work_oship")
    @Expose
    private String flexWorkOship;
    @SerializedName("current_salary")
    @Expose
    private String currentSalary;
    @SerializedName("expect_salary")
    @Expose
    private String expectSalary;
    @SerializedName("pref_cduration")
    @Expose
    private String prefCduration;
    @SerializedName("pref_leave_dur")
    @Expose
    private String prefLeaveDur;
    @SerializedName("other_pref")
    @Expose
    private String otherPref;
    @SerializedName("ship_size")
    @Expose
    private String shipSize;
    @SerializedName("ship_age")
    @Expose
    private String shipAge;
    @SerializedName("tranding_area")
    @Expose
    private String tradingArea;
    @SerializedName("food")
    @Expose
    private String food;
    @SerializedName("fam_carrg")
    @Expose
    private String famCarrg;
    @SerializedName("salary")
    @Expose
    private String salary;
    @SerializedName("promotion")
    @Expose
    private String promotion;
    @SerializedName("vacation")
    @Expose
    private String vacation;
    @SerializedName("reg_emp")
    @Expose
    private String regEmp;
    @SerializedName("data")
    @Expose
    private List<FullExpRequest> data = null;


    public String getApiAccessKey() {
        return apiAccessKey;
    }

    public void setApiAccessKey(String apiAccessKey) {
        this.apiAccessKey = apiAccessKey;
    }

    public String getIndosNo() {
        return indosNo;
    }

    public void setIndosNo(String indosNo) {
        this.indosNo = indosNo;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRankappliedfor() {
        return rankappliedfor;
    }

    public void setRankappliedfor(String rankappliedfor) {
        this.rankappliedfor = rankappliedfor;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


    public String getScCandRegisterFlag() {
        return scCandRegisterFlag;
    }

    public void setScCandRegisterFlag(String scCandRegisterFlag) {
        this.scCandRegisterFlag = scCandRegisterFlag;
    }

    public String getSailingDate() {
        return sailingDate;
    }

    public void setSailingDate(String sailingDate) {
        this.sailingDate = sailingDate;
    }

    public String getLeaveDur() {
        return leaveDur;
    }

    public void setLeaveDur(String leaveDur) {
        this.leaveDur = leaveDur;
    }

    public String getCdiscontinuety() {
        return cdiscontinuety;
    }

    public void setCdiscontinuety(String cdiscontinuety) {
        this.cdiscontinuety = cdiscontinuety;
    }

    public String getCreason() {
        return creason;
    }

    public void setCreason(String creason) {
        this.creason = creason;
    }

    public String getShipTypeWorked() {
        return shipTypeWorked;
    }

    public void setShipTypeWorked(String shipTypeWorked) {
        this.shipTypeWorked = shipTypeWorked;
    }

    public String getFlexWorkOship() {
        return flexWorkOship;
    }

    public void setFlexWorkOship(String flexWorkOship) {
        this.flexWorkOship = flexWorkOship;
    }

    public String getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(String currentSalary) {
        this.currentSalary = currentSalary;
    }

    public String getExpectSalary() {
        return expectSalary;
    }

    public void setExpectSalary(String expectSalary) {
        this.expectSalary = expectSalary;
    }

    public String getPrefCduration() {
        return prefCduration;
    }

    public void setPrefCduration(String prefCduration) {
        this.prefCduration = prefCduration;
    }

    public String getPrefLeaveDur() {
        return prefLeaveDur;
    }

    public void setPrefLeaveDur(String prefLeaveDur) {
        this.prefLeaveDur = prefLeaveDur;
    }

    public String getOtherPref() {
        return otherPref;
    }

    public void setOtherPref(String otherPref) {
        this.otherPref = otherPref;
    }

    public String getShipSize() {
        return shipSize;
    }

    public void setShipSize(String shipSize) {
        this.shipSize = shipSize;
    }

    public String getShipAge() {
        return shipAge;
    }

    public void setShipAge(String shipAge) {
        this.shipAge = shipAge;
    }

    public String getTradingArea() {
        return tradingArea;
    }

    public void setTradingArea(String tradingArea) {
        this.tradingArea = tradingArea;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getFamCarrg() {
        return famCarrg;
    }

    public void setFamCarrg(String famCarrg) {
        this.famCarrg = famCarrg;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getVacation() {
        return vacation;
    }

    public void setVacation(String vacation) {
        this.vacation = vacation;
    }

    public String getRegEmp() {
        return regEmp;
    }

    public void setRegEmp(String regEmp) {
        this.regEmp = regEmp;
    }

    public List<FullExpRequest> getData() {
        return data;
    }

    public void setData(List<FullExpRequest> data) {
        this.data = data;
    }
}
