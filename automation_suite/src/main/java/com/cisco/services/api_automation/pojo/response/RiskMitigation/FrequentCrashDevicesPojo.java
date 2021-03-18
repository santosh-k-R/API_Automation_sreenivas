package com.cisco.services.api_automation.pojo.response.RiskMitigation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FrequentCrashDevicesPojo {
	@SerializedName("resetReason")
	@Expose
	private String resetReason;
	@SerializedName("timeStamp")
	@Expose
	private String timeStamp;

	public String getResetReason() {
	return resetReason;
	}

	public void setResetReason(String resetReason) {
	this.resetReason = resetReason;
	}

	public String getTimeStamp() {
	return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
	this.timeStamp = timeStamp;
	}

}
