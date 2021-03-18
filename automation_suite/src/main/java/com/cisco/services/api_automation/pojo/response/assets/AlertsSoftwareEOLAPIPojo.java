package com.cisco.services.api_automation.pojo.response.assets;

public class AlertsSoftwareEOLAPIPojo {
	String customerId;
	String managedNeId;
	String neInstanceId;
	String productId;
	String equipmentType;
	String swType;
	String swVersion;
	String swEolInstanceId;
	String bulletinHeadline;
	String cxLevel;
	String saId;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getManagedNeId() {
		return managedNeId;
	}
	public void setManagedNeId(String managedNeId) {
		this.managedNeId = managedNeId;
	}
	public String getNeInstanceId() {
		return neInstanceId;
	}
	public void setNeInstanceId(String neInstanceId) {
		this.neInstanceId = neInstanceId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
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
	public String getSwEolInstanceId() {
		return swEolInstanceId;
	}
	public void setSwEolInstanceId(String swEolInstanceId) {
		this.swEolInstanceId = swEolInstanceId;
	}
	public String getBulletinHeadline() {
		return bulletinHeadline;
	}
	public void setBulletinHeadline(String bulletinHeadline) {
		this.bulletinHeadline = bulletinHeadline;
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
	@Override
	public String toString() {
		return "AlertsSoftwareEOLAPIPojo [customerId=" + customerId + ", managedNeId=" + managedNeId + ", neInstanceId="
				+ neInstanceId + ", productId=" + productId + ", equipmentType=" + equipmentType + ", swType=" + swType
				+ ", swVersion=" + swVersion + ", swEolInstanceId=" + swEolInstanceId + ", bulletinHeadline="
				+ bulletinHeadline + ", cxLevel=" + cxLevel + ", saId=" + saId + "]";
	}


}
