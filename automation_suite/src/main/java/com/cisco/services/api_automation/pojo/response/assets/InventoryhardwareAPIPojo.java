package com.cisco.services.api_automation.pojo.response.assets;

public class InventoryhardwareAPIPojo {
	
	String collectorId;
	String managedNeId;
	String hostname;
	String managementAddress;
	String hwInstanceId;
	String containingHwId;
	String serialNumber;
	String productId;
	String productFamily;
	String productType;
	String productName;
	String equipmentType;
	String productDescription;
	String swType;
	String swVersion;
	int tags;
	String wfId;
	String cxLevel;
	String saId;
	String mgmtSystemId;
	String mgmtSystemAddr;
	String mgmtSystemHostname;
	String mgmtSystemType;
	String sourceNeId;
	String isCollector;
	
	
	public String getCollectorId() {
		return collectorId;
	}
	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}
	public String getManagedNeId() {
		return managedNeId;
	}
	public void setManagedNeId(String managedNeId) {
		this.managedNeId = managedNeId;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getManagementAddress() {
		return managementAddress;
	}
	public void setManagementAddress(String managementAddress) {
		this.managementAddress = managementAddress;
	}
	public String getHwInstanceId() {
		return hwInstanceId;
	}
	public void setHwInstanceId(String hwInstanceId) {
		this.hwInstanceId = hwInstanceId;
	}
	public String getContainingHwId() {
		return containingHwId;
	}
	public void setContainingHwId(String containingHwId) {
		this.containingHwId = String.valueOf(containingHwId);
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductFamily() {
		return productFamily;
	}
	public void setProductFamily(String productFamily) {
		this.productFamily = productFamily;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getSwType() {
		return swType;
	}
	public void setSwType(String swType) {
		this.swType = swType;
	}
	public String getSwVersion() {
		return swVersion;
	}
	public void setSwVersion(String swVersion) {
		this.swVersion = swVersion;
	}
	public int getTags() {
		return tags;
	}
	public void setTags(int tags) {
		this.tags = tags;
	}
	public String getWfId() {
		return wfId;
	}
	public void setWfId(String wfId) {
		this.wfId = wfId;
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
	public void setSaId(int saId) {
		this.saId = String.valueOf(saId);
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
	public String getSourceNeId() {
		return sourceNeId;
	}
	public void setSourceNeId(String sourceNeId) {
		this.sourceNeId = sourceNeId;
	}
	public String getIsCollector() {
		return isCollector;
	}
	public void setIsCollector(String isCollector) {
		this.isCollector = isCollector;
	}
	public String NeInstanceId() {
		return isCollector;
	}

	@Override
	public String toString() {
		return "InventoryhardwareAPIPojo [collectorId=" + collectorId + ", managedNeId=" + managedNeId + ", hostname="
				+ hostname + ", managementAddress=" + managementAddress + ", hwInstanceId=" + hwInstanceId
				+ ", containingHwId=" + containingHwId + ", serialNumber=" + serialNumber + ", productId=" + productId
				+ ", productFamily=" + productFamily + ", productType=" + productType + ", productName=" + productName
				+ ", equipmentType=" + equipmentType + ", productDescription=" + productDescription + ", swType="
				+ swType + ", swVersion=" + swVersion + ", tags=" + tags + ", wfId=" + wfId + ", cxLevel=" + cxLevel
				+ ", saId=" + saId + ", mgmtSystemId=" + mgmtSystemId + ", mgmtSystemAddr=" + mgmtSystemAddr
				+ ", mgmtSystemHostname=" + mgmtSystemHostname + ", mgmtSystemType=" + mgmtSystemType + ", sourceNeId="
				+ sourceNeId + ", isCollector=" + isCollector + "]";
	}
	
	
	
}
