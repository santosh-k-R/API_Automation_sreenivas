package com.cisco.services.api_automation.pojo.response.RiskMitigation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CrashedRiskDevicesPojo {
	@SerializedName("customerId")
    @Expose
	private String customerId;
	
	@SerializedName("count")
    @Expose
	private int count;

	@SerializedName("devices")
    @Expose
    private List<DevicesPojo> devices;

    public void setCustomerId(String customerId){
        this.customerId = customerId;
    }
    public String getCustomerId(){
        return this.customerId;
    }
    
    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    
    public void setDevices(List<DevicesPojo> devices){
        this.devices = devices;
    }
    public List<DevicesPojo> getDevices(){
        return this.devices;
    }
}
