package com.example.seajobnowcandidate.Model;

public class FullExperience {

    String ship_name;
    String vessel_type;
    String engine_type;
    String engine_power;
    String fromdate,to_date;
    String total_time;
    String reason_stick;
    String change_reason;
    String circumtance;
    String signed_off;
    String surgery;
    String achivement;

    public FullExperience(String ship_name, String vessel_type, String engine_type, String engine_power,
                          String fromdate, String to_date, String total_time,
                          String reason_stick, String change_reason, String circumtance,
                          String signed_off, String surgery, String achivement) {
        this.ship_name = ship_name;
        this.vessel_type = vessel_type;
        this.engine_type = engine_type;
        this.engine_power = engine_power;
        this.fromdate = fromdate;
        this.to_date = to_date;
        this.total_time = total_time;
        this.reason_stick = reason_stick;
        this.change_reason = change_reason;
        this.circumtance = circumtance;
        this.signed_off = signed_off;
        this.surgery = surgery;
        this.achivement = achivement;
    }

    public String getShip_name() {
        return ship_name;
    }

    public void setShip_name(String ship_name) {
        this.ship_name = ship_name;
    }

    public String getVessel_type() {
        return vessel_type;
    }

    public void setVessel_type(String vessel_type) {
        this.vessel_type = vessel_type;
    }

    public String getEngine_type() {
        return engine_type;
    }

    public void setEngine_type(String engine_type) {
        this.engine_type = engine_type;
    }

    public String getEngine_power() {
        return engine_power;
    }

    public void setEngine_power(String engine_power) {
        this.engine_power = engine_power;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getTotal_time() {
        return total_time;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    public String getReason_stick() {
        return reason_stick;
    }

    public void setReason_stick(String reason_stick) {
        this.reason_stick = reason_stick;
    }

    public String getChange_reason() {
        return change_reason;
    }

    public void setChange_reason(String change_reason) {
        this.change_reason = change_reason;
    }

    public String getCircumtance() {
        return circumtance;
    }

    public void setCircumtance(String circumtance) {
        this.circumtance = circumtance;
    }

    public String getSigned_off() {
        return signed_off;
    }

    public void setSigned_off(String signed_off) {
        this.signed_off = signed_off;
    }

    public String getSurgery() {
        return surgery;
    }

    public void setSurgery(String surgery) {
        this.surgery = surgery;
    }

    public String getAchivement() {
        return achivement;
    }

    public void setAchivement(String achivement) {
        this.achivement = achivement;
    }

    @Override
    public String toString() {
        return "FullExperience{" +
                "ship_name='" + ship_name + '\'' +
                ", vessel_type='" + vessel_type + '\'' +
                ", engine_type='" + engine_type + '\'' +
                ", engine_power='" + engine_power + '\'' +
                ", fromdate='" + fromdate + '\'' +
                ", to_date='" + to_date + '\'' +
                ", total_time='" + total_time + '\'' +
                ", reason_stick='" + reason_stick + '\'' +
                ", change_reason='" + change_reason + '\'' +
                ", circumtance='" + circumtance + '\'' +
                ", signed_off='" + signed_off + '\'' +
                ", surgery='" + surgery + '\'' +
                ", achivement='" + achivement + '\'' +
                '}';
    }
}
