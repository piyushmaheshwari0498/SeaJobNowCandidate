package com.example.seajobnowcandidate.Entity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpinnerData {
    @SerializedName("city")
    @Expose
    private List<CityRequest> city = null;
    @SerializedName("state")
    @Expose
    private List<StateRequest> state = null;
    @SerializedName("country")
    @Expose
    private List<CountryRequest> country = null;

    public List<CityRequest> getCity() {
        return city;
    }

    public void setCity(List<CityRequest> city) {
        this.city = city;
    }

    public List<StateRequest> getState() {
        return state;
    }

    public void setState(List<StateRequest> state) {
        this.state = state;
    }

    public List<CountryRequest> getCountry() {
        return country;
    }

    public void setCountry(List<CountryRequest> country) {
        this.country = country;
    }

}
