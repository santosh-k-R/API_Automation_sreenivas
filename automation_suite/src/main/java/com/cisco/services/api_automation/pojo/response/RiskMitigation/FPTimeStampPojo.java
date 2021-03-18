package com.cisco.services.api_automation.pojo.response.RiskMitigation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FPTimeStampPojo {
	@SerializedName("customerId")
	@Expose
	private String customerId;
	@SerializedName("startTime")
	@Expose
	private String startTime;
	@SerializedName("endTime")
	@Expose
	private String endTime;
	@SerializedName("status")
	@Expose
	private String status;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
