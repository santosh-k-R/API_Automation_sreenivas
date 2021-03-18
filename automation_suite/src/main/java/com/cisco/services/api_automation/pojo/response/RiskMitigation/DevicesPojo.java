package com.cisco.services.api_automation.pojo.response.RiskMitigation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DevicesPojo {

	@SerializedName("deviceId")
	@Expose
	private String deviceId;


	@SerializedName("riskScore")
	@Expose
	private String riskScore;

	@SerializedName("globalRiskRank")
	@Expose
	private String globalRiskRank;

	@SerializedName("productFamily")
	@Expose
	private String productFamily;

	@SerializedName("softwareVersion")
	@Expose
	private String softwareVersion;
	
	@SerializedName("softwareType")
	@Expose
	private String softwareType;

	@SerializedName("productId")
	@Expose
	private String productId;

	@SerializedName("deviceName")
	@Expose
	private String deviceName;

	@SerializedName("serialNumber")
	@Expose
	private String serialNumber;

	public void setdeviceId(String deviceId){
		this.deviceId = deviceId;
	}
	public String getdeviceId(){
		return this.deviceId;
	}
	
	public void setRiskScore(String riskScore){
		this.riskScore = riskScore;
	}
	public String getRiskScore(){
		return this.riskScore;
	}
	public void setGlobalRiskRank(String globalRiskRank){
		this.globalRiskRank = globalRiskRank;
	}
	public String getGlobalRiskRank(){
		return this.globalRiskRank;
	}
	public void setProductFamily(String productFamily){
		this.productFamily = productFamily;
	}
	public String getProductFamily(){
		return this.productFamily;
	}
	public void setSoftwareVersion(String softwareVersion){
		this.softwareVersion = softwareVersion;
	}
	public String getSoftwareVersion(){
		return this.softwareVersion;
	}
	public void setSoftwareType(String softwareType){
		this.softwareType = softwareType;
	}
	public String getSoftwareType(){
		return this.softwareType;
	}
	public void setProductId(String productId){
		this.productId = productId;
	}
	public String getProductId(){
		return this.productId;
	}
	public void setDeviceName(String deviceName){
		this.deviceName = deviceName;
	}
	public String getDeviceName(){
		return this.deviceName;
	}
	public void setSerialNumber(String serialNumber){
		this.serialNumber = serialNumber;
	}
	public String getSerialNumber(){
		return this.serialNumber;
	}
}
