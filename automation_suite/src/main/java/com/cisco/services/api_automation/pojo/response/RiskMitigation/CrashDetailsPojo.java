package com.cisco.services.api_automation.pojo.response.RiskMitigation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CrashDetailsPojo {
	@SerializedName("customerId")
    @Expose
    private String customerId;
    @SerializedName("timePeriod")
    @Expose
    private String timePeriod;
    @SerializedName("deviceDetails")
    @Expose
    private List<DeviceDetailsPojo> deviceDetails = null;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public List<DeviceDetailsPojo> getDeviceDetails() {
        return deviceDetails;
    }

    public void setDeviceDetails(List<DeviceDetailsPojo> deviceDetails) {
        this.deviceDetails = deviceDetails;
    }
}
