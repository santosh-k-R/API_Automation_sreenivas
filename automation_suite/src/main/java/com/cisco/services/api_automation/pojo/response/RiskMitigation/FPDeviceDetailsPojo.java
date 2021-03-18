package com.cisco.services.api_automation.pojo.response.RiskMitigation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FPDeviceDetailsPojo {
	@SerializedName("customerId")
	@Expose
	private String customerId;
	@SerializedName("deviceId")
	@Expose
	private String deviceId;
	@SerializedName("deviceName")
	@Expose
	private String deviceName;
	@SerializedName("ipAddress")
	@Expose
	private String ipAddress;
	@SerializedName("serialNumber")
	@Expose
	private String serialNumber;
	@SerializedName("globalRiskRank")
	@Expose
	private String globalRiskRank;
	@SerializedName("riskScore")
	@Expose
	private String riskScore;
	@SerializedName("productFamily")
	@Expose
	private String productFamily;
	@SerializedName("productId")
	@Expose
	private String productId;
	@SerializedName("softwareVersion")
	@Expose
	private String softwareVersion;
	@SerializedName("softwareType")
	@Expose
	private String softwareType;
	@SerializedName("crashPredicted")
	@Expose
	private Boolean crashPredicted;

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

	public String getDeviceName() {
	return deviceName;
	}

	public void setDeviceName(String deviceName) {
	this.deviceName = deviceName;
	}

	public String getIpAddress() {
	return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
	this.ipAddress = ipAddress;
	}

	public String getSerialNumber() {
	return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
	this.serialNumber = serialNumber;
	}

	public String getGlobalRiskRank() {
	return globalRiskRank;
	}

	public void setGlobalRiskRank(String globalRiskRank) {
	this.globalRiskRank = globalRiskRank;
	}

	public String getRiskScore() {
	return riskScore;
	}

	public void setRiskScore(String riskScore) {
	this.riskScore = riskScore;
	}

	public String getProductFamily() {
	return productFamily;
	}

	public void setProductFamily(String productFamily) {
	this.productFamily = productFamily;
	}

	public String getProductId() {
	return productId;
	}

	public void setProductId(String productId) {
	this.productId = productId;
	}

	public String getSoftwareVersion() {
	return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
	this.softwareVersion = softwareVersion;
	}

	public String getSoftwareType() {
	return softwareType;
	}

	public void setSoftwareType(String softwareType) {
	this.softwareType = softwareType;
	}

	public Boolean getCrashPredicted() {
	return crashPredicted;
	}

	public void setCrashPredicted(Boolean crashPredicted) {
	this.crashPredicted = crashPredicted;
	}
}
