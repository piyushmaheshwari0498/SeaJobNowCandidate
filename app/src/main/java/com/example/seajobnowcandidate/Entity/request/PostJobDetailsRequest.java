package com.example.seajobnowcandidate.Entity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostJobDetailsRequest {
    @SerializedName("cjm_id")
    @Expose
    private String cjmId;
    @SerializedName("cjm_comp_id")
    @Expose
    private String cjmCompId;
    @SerializedName("cjm_comp_user_id")
    @Expose
    private String cjmCompUserId;
    @SerializedName("cjm_jobPost_code")
    @Expose
    private String cjmJobPostCode;
    @SerializedName("cjm_post_name")
    @Expose
    private String cjmPostName;
    @SerializedName("cjm_description")
    @Expose
    private String cjmDescription;
    @SerializedName("cjm_job_openings")
    @Expose
    private String cjmJobOpenings;
    @SerializedName("cjm_start_date")
    @Expose
    private String cjmStartDate;
    @SerializedName("cjm_expiry_date")
    @Expose
    private String cjmExpiryDate;
    @SerializedName("cjm_experience_in_months")
    @Expose
    private String cjmExperienceInMonths;
    @SerializedName("cjm_emp_type")
    @Expose
    private String cjmEmpType;
    @SerializedName("cjm_salary")
    @Expose
    private String cjmSalary;
    @SerializedName("cjm_department")
    @Expose
    private String cjmDepartment;
    @SerializedName("cjm_rank")
    @Expose
    private String cjmRank;
    @SerializedName("cjm_vessel_type")
    @Expose
    private String cjmVesselType;
    @SerializedName("cjm_job_location")
    @Expose
    private String cjmJobLocation;
    @SerializedName("cjm_status")
    @Expose
    private String cjmStatus;

    public PostJobDetailsRequest(String job_title, String department,
                    String rank, String salary, String ship_type, String location, String start_date, String end_date) {
        this.cjmPostName = job_title;
        this.cjmDepartment = department;
        this.cjmRank = rank;
        this.cjmSalary = salary;
        this.cjmVesselType = ship_type;
        this.cjmJobLocation = location;
        this.cjmStartDate = start_date;
        this.cjmExpiryDate = end_date;
    }

    public String getCjmId() {
        return cjmId;
    }

    public void setCjmId(String cjmId) {
        this.cjmId = cjmId;
    }

    public String getCjmCompId() {
        return cjmCompId;
    }

    public void setCjmCompId(String cjmCompId) {
        this.cjmCompId = cjmCompId;
    }

    public String getCjmCompUserId() {
        return cjmCompUserId;
    }

    public void setCjmCompUserId(String cjmCompUserId) {
        this.cjmCompUserId = cjmCompUserId;
    }

    public String getCjmJobPostCode() {
        return cjmJobPostCode;
    }

    public void setCjmJobPostCode(String cjmJobPostCode) {
        this.cjmJobPostCode = cjmJobPostCode;
    }

    public String getCjmPostName() {
        return cjmPostName;
    }

    public void setCjmPostName(String cjmPostName) {
        this.cjmPostName = cjmPostName;
    }

    public String getCjmDescription() {
        return cjmDescription;
    }

    public void setCjmDescription(String cjmDescription) {
        this.cjmDescription = cjmDescription;
    }

    public String getCjmJobOpenings() {
        return cjmJobOpenings;
    }

    public void setCjmJobOpenings(String cjmJobOpenings) {
        this.cjmJobOpenings = cjmJobOpenings;
    }

    public String getCjmStartDate() {
        return cjmStartDate;
    }

    public void setCjmStartDate(String cjmStartDate) {
        this.cjmStartDate = cjmStartDate;
    }

    public String getCjmExpiryDate() {
        return cjmExpiryDate;
    }

    public void setCjmExpiryDate(String cjmExpiryDate) {
        this.cjmExpiryDate = cjmExpiryDate;
    }

    public String getCjmExperienceInMonths() {
        return cjmExperienceInMonths;
    }

    public void setCjmExperienceInMonths(String cjmExperienceInMonths) {
        this.cjmExperienceInMonths = cjmExperienceInMonths;
    }

    public String getCjmEmpType() {
        return cjmEmpType;
    }

    public void setCjmEmpType(String cjmEmpType) {
        this.cjmEmpType = cjmEmpType;
    }

    public String getCjmSalary() {
        return cjmSalary;
    }

    public void setCjmSalary(String cjmSalary) {
        this.cjmSalary = cjmSalary;
    }

    public String getCjmDepartment() {
        return cjmDepartment;
    }

    public void setCjmDepartment(String cjmDepartment) {
        this.cjmDepartment = cjmDepartment;
    }

    public String getCjmRank() {
        return cjmRank;
    }

    public void setCjmRank(String cjmRank) {
        this.cjmRank = cjmRank;
    }

    public String getCjmVesselType() {
        return cjmVesselType;
    }

    public void setCjmVesselType(String cjmVesselType) {
        this.cjmVesselType = cjmVesselType;
    }

    public String getCjmJobLocation() {
        return cjmJobLocation;
    }

    public void setCjmJobLocation(String cjmJobLocation) {
        this.cjmJobLocation = cjmJobLocation;
    }

    public String getCjmStatus() {
        return cjmStatus;
    }

    public void setCjmStatus(String cjmStatus) {
        this.cjmStatus = cjmStatus;
    }

    @Override
    public String toString() {
        return "PostJobDetailsRequest{" +
                "cjmId='" + cjmId + '\'' +
                ", cjmCompId='" + cjmCompId + '\'' +
                ", cjmCompUserId='" + cjmCompUserId + '\'' +
                ", cjmJobPostCode='" + cjmJobPostCode + '\'' +
                ", cjmPostName='" + cjmPostName + '\'' +
                ", cjmDescription='" + cjmDescription + '\'' +
                ", cjmJobOpenings='" + cjmJobOpenings + '\'' +
                ", cjmStartDate='" + cjmStartDate + '\'' +
                ", cjmExpiryDate='" + cjmExpiryDate + '\'' +
                ", cjmExperienceInMonths='" + cjmExperienceInMonths + '\'' +
                ", cjmEmpType='" + cjmEmpType + '\'' +
                ", cjmSalary='" + cjmSalary + '\'' +
                ", cjmDepartment='" + cjmDepartment + '\'' +
                ", cjmRank='" + cjmRank + '\'' +
                ", cjmVesselType='" + cjmVesselType + '\'' +
                ", cjmJobLocation='" + cjmJobLocation + '\'' +
                ", cjmStatus='" + cjmStatus + '\'' +
                '}';
    }
}
