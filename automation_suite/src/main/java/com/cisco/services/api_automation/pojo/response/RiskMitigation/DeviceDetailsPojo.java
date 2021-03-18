package com.cisco.services.api_automation.pojo.response.RiskMitigation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeviceDetailsPojo {
	 @SerializedName("neInstanceId")
	    @Expose
	    private String neInstanceId;
	    @SerializedName("neName")
	    @Expose
	    private String neName;
	    @SerializedName("ipAddress")
	    @Expose
	    private Object ipAddress;
	    @SerializedName("productFamily")
	    @Expose
	    private String productFamily;
	    @SerializedName("productId")
	    @Expose
	    private String productId;
	    @SerializedName("swVersion")
	    @Expose
	    private String swVersion;
	    @SerializedName("swType")
	    @Expose
	    private String swType;
	    @SerializedName("serialNumber")
	    @Expose
	    private Object serialNumber;
	    @SerializedName("firstOccurrence")
	    @Expose
	    private String firstOccurrence;
	    @SerializedName("lastOccurrence")
	    @Expose
	    private String lastOccurrence;
	    @SerializedName("crashCount")
	    @Expose
	    private Integer crashCount;

	    private List<DeviceDetailsPojo> deviceDetails = null;
	    
	    public String getNeInstanceId() {
	        return neInstanceId;
	    }

	    public void setNeInstanceId(String neInstanceId) {
	        this.neInstanceId = neInstanceId;
	    }

	    public String getNeName() {
	        return neName;
	    }

	    public void setNeName(String neName) {
	        this.neName = neName;
	    }

	    public Object getIpAddress() {
	        return ipAddress;
	    }

	    public void setIpAddress(Object ipAddress) {
	        this.ipAddress = ipAddress;
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

	    public String getSwVersion() {
	        return swVersion;
	    }

	    public void setSwVersion(String swVersion) {
	        this.swVersion = swVersion;
	    }
	    public String getSwType() {
	        return swType;
	    }

	    public void setSwType(String swType) {
	        this.swType = swType;
	    }
	    public Object getSerialNumber() {
	        return serialNumber;
	    }

	    public void setSerialNumber(Object serialNumber) {
	        this.serialNumber = serialNumber;
	    }

	    public String getFirstOccurrence() {
	        return firstOccurrence;
	    }

	    public void setFirstOccurrence(String firstOccurrence) {
	        this.firstOccurrence = firstOccurrence;
	    }

	    public String getLastOccurrence() {
	        return lastOccurrence;
	    }

	    public void setLastOccurrence(String lastOccurrence) {
	        this.lastOccurrence = lastOccurrence;
	    }

	    public Integer getCrashCount() {
	        return crashCount;
	    }

	    public void setCrashCount(Integer crashCount) {
	        this.crashCount = crashCount;
	    }
	    public List<DeviceDetailsPojo> getDeviceDetails() {
	        return deviceDetails;
	    }

	    public void setDeviceDetails(List<DeviceDetailsPojo> deviceDetails) {
	        this.deviceDetails = deviceDetails;
	    }
}
