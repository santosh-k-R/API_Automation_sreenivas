package com.cisco.services.api_automation.pojo.response.assets;

public class InventoryAssetsSystemAPIPojo {
	
	String customerId;
	String deviceName;
	String ipAddress;
	String collectorId;
	String wfId;
	String criticalAdvisories;
	String serialNumber;
	String osType;
	String osVersion;
	String role;
	String managedNeId;
	String hasBugs;
	String isManagedNE;
	String productId;
	String productName;
	String productType;
	String productFamily;
	String neId;
	String sourceNeId;
	String isCollector;
	String cxLevel;
	String saId;
	String mgmtSystemId;
	String mgmtSystemAddr;
	String mgmtSystemHostname;
	String mgmtSystemType;
	String hasSecurityAdvisories;
	String advisory;
	String assetCategory;
	String supportCoverage;
	String connectivityStatus;
	String location;
	String license;
	String isScanCapable;
	String scanStatus;
	String lastScan;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	public String getCollectorId() {
		return collectorId;
	}
	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}
	public String getWfId() {
		return wfId;
	}
	public void setWfId(String wfId) {
		this.wfId = wfId;
	}
	public String getCriticalAdvisories() {
		return criticalAdvisories;
	}
	public void setCriticalAdvisories(String criticalAdvisories) {
		this.criticalAdvisories = criticalAdvisories;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		if (role==null)
		this.role = "";
		else
			this.role = role;
	}
	public String getManagedNeId() {
		return managedNeId;
	}
	public void setManagedNeId(String managedNeId) {
		this.managedNeId = managedNeId;
	}
	public String getHasBugs() {
		return hasBugs;
	}
	public void setHasBugs(String hasBugs) {
		this.hasBugs = hasBugs;
	}
	public String getIsManagedNE() {
		return isManagedNE;
	}
	public void setIsManagedNE(String isManagedNE) {
		this.isManagedNE = isManagedNE;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = String.valueOf(productName);
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductFamily() {
		return productFamily;
	}
	public void setProductFamily(String productFamily) {
		this.productFamily = productFamily;
	}
	public String getNeId() {
		return neId;
	}
	public void setNeId(String neId) {
		this.neId = neId;
	}
	public String getSourceNeId() {
		return sourceNeId;
	}
	public void setSourceNeId(String sourceNeId) {
		this.sourceNeId = String.valueOf(sourceNeId);
	}
	public String getIsCollector() {
		return isCollector;
	}
	public void setIsCollector(String isCollector) {
		this.isCollector = isCollector;
	}
	public String getCxLevel() {
		return cxLevel;
	}
	public void setCxLevel(String cxLevel) {
		this.cxLevel = cxLevel;
	}
	public String getSaId() {
		return saId;
	}
	public void setSaId(String saId) {
		this.saId = saId;
	}
	public String getMgmtSystemId() {
		return mgmtSystemId;
	}
	public void setMgmtSystemId(String mgmtSystemId) {
		this.mgmtSystemId = mgmtSystemId;
	}
	public String getMgmtSystemAddr() {
		return mgmtSystemAddr;
	}
	public void setMgmtSystemAddr(String mgmtSystemAddr) {
		this.mgmtSystemAddr = mgmtSystemAddr;
	}
	public String getMgmtSystemHostname() {
		return mgmtSystemHostname;
	}
	public void setMgmtSystemHostname(String mgmtSystemHostname) {
		this.mgmtSystemHostname = mgmtSystemHostname;
	}
	public String getMgmtSystemType() {
		return mgmtSystemType;
	}
	public void setMgmtSystemType(String mgmtSystemType) {
		this.mgmtSystemType = mgmtSystemType;
	}
	public String getHasSecurityAdvisories() {
		return hasSecurityAdvisories;
	}
	public void setHasSecurityAdvisories(String hasSecurityAdvisories) {
		this.hasSecurityAdvisories = hasSecurityAdvisories;
	}
	public String getAdvisory() {
		return advisory;
	}
	public void setAdvisory(String advisory) {
		this.advisory = advisory;
	}
	public String getAssetCategory() {
		return assetCategory;
	}
	public void setAssetCategory(String assetCategory) {
		this.assetCategory = assetCategory;
	}
	public String getSupportCoverage() {
		return supportCoverage;
	}
	public void setSupportCoverage(String supportCoverage) {
		this.supportCoverage = supportCoverage;
	}
	public String getConnectivityStatus() {
		return connectivityStatus;
	}
	public void setConnectivityStatus(String connectivityStatus) {
		this.connectivityStatus = connectivityStatus;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getIsScanCapable() {
		return isScanCapable;
	}
	public void setIsScanCapable(String isScanCapable) {
		this.isScanCapable = isScanCapable;
	}
	public String getScanStatus() {
		return scanStatus;
	}
	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
	}
	public String getLastScan() {
		return lastScan;
	}
	public void setLastScan(String lastScan) {
		this.lastScan = lastScan;
	}
	@Override
	public String toString() {
		return "InventoryAssetsSystemAPIPojo [customerId=" + customerId + ", deviceName=" + deviceName + ", ipAddress="
				+ ipAddress + ", collectorId=" + collectorId + ", wfId=" + wfId + ", criticalAdvisories="
				+ criticalAdvisories + ", serialNumber=" + serialNumber + ", osType=" + osType + ", osVersion="
				+ osVersion + ", role=" + role + ", managedNeId=" + managedNeId + ", hasBugs=" + hasBugs
				+ ", isManagedNE=" + isManagedNE + ", productId=" + productId + ", productName=" + productName
				+ ", productType=" + productType + ", productFamily=" + productFamily + ", neId=" + neId
				+ ", sourceNeId=" + sourceNeId + ", isCollector=" + isCollector + ", cxLevel=" + cxLevel + ", saId="
				+ saId + ", mgmtSystemId=" + mgmtSystemId + ", mgmtSystemAddr=" + mgmtSystemAddr
				+ ", mgmtSystemHostname=" + mgmtSystemHostname + ", mgmtSystemType=" + mgmtSystemType
				+ ", hasSecurityAdvisories=" + hasSecurityAdvisories + ", advisory=" + advisory + ", assetCategory="
				+ assetCategory + ", supportCoverage=" + supportCoverage + ", connectivityStatus=" + connectivityStatus
				+ ", location=" + location + ", license=" + license + ", isScanCapable=" + isScanCapable
				+ ", scanStatus=" + scanStatus + ", lastScan=" + lastScan + "]";
	}

}