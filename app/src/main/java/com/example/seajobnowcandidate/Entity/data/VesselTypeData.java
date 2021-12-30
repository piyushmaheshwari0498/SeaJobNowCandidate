package com.example.seajobnowcandidate.Entity.data;

import com.example.seajobnowcandidate.Entity.request.VesselRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VesselTypeData {

    @SerializedName("vesseltype")
    @Expose
    private List<VesselRequest> vesseltype = null;

    public List<VesselRequest> getVesseltype() {
        return vesseltype;
    }

    public void setVesseltype(List<VesselRequest> vesseltype) {
        this.vesseltype = vesseltype;
    }
}
