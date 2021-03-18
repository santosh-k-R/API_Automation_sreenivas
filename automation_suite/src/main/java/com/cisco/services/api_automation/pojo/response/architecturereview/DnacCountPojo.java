package com.cisco.services.api_automation.pojo.response.architecturereview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DnacCountPojo {
	@SerializedName("totalCount")
	@Expose
	private int totalCount;

    private List<DnacDetailsPojo> dnacDetails;
    
    private List<DnacDeviceDetailsPojo> dnacDeviceDetails;
    
    public void setTotalCount(int totalCount){
        this.totalCount = totalCount;
    }
    public int getTotalCount(){
        return this.totalCount;
    }
    public void setDnacDetails(List<DnacDetailsPojo> dnacDetails){
        this.dnacDetails = dnacDetails;
    }
    public List<DnacDetailsPojo> getDnacDetails(){
        return this.dnacDetails;
    }
    public void setDnacDeviceDetails(List<DnacDeviceDetailsPojo> dnacDeviceDetails){
        this.dnacDeviceDetails = dnacDeviceDetails;
    }
    public List<DnacDeviceDetailsPojo> getDnacDeviceDetails(){
        return this.dnacDeviceDetails;
    }
}