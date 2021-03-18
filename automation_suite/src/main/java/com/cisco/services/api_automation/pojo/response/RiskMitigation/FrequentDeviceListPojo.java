package com.cisco.services.api_automation.pojo.response.RiskMitigation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FrequentDeviceListPojo {

	@SerializedName("customerId")
	@Expose
	private String customerId;
	@SerializedName("deviceId")
	@Expose
	private String deviceId;
	@SerializedName("crashes")
	@Expose
	private List<FrequentCrashDevicesPojo> crashes = null;

	public String getCustomerId() {
	return customerId;
	}

	public void setCustomerId(String customerId) {
	this.customerId = customerId;
	}

	public String getDeviceId() {
	return deviceId;
	}

	public void setDeviceId(String deviceId) {
	this.deviceId = deviceId;
	}

	public List<FrequentCrashDevicesPojo> getCrashes() {
	return crashes;
	}

	public void setCrashes(List<FrequentCrashDevicesPojo> crashes) {
	this.crashes = crashes;
	}
}
