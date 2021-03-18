package com.cisco.services.api_automation.pojo.response.assets;

public class AlertsHardwareEOLAPIPojo {
	String customerId;
	String managedNeId;
	String neInstanceId;
	String hwInstanceId;
	String productId;
	String equipmentType;
	String hwEolInstanceId;
	String bulletinName;
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
	public String getHwInstanceId() {
		return hwInstanceId;
	}
	public void setHwInstanceId(String hwInstanceId) {
		this.hwInstanceId = hwInstanceId;
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
	public String getHwEolInstanceId() {
		return hwEolInstanceId;
	}
	public void setHwEolInstanceId(String hwEolInstanceId) {
		this.hwEolInstanceId = hwEolInstanceId;
	}
	public String getBulletinName() {
		return bulletinName;
	}
	public void setBulletinName(String bulletinName) {
		this.bulletinName = bulletinName;
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
		return "AlertsHardwareEOLAPIPojo [customerId=" + customerId + ", managedNeId=" + managedNeId + ", neInstanceId="
				+ neInstanceId + ", hwInstanceId=" + hwInstanceId + ", productId=" + productId + ", equipmentType="
				+ equipmentType + ", hwEolInstanceId=" + hwEolInstanceId + ", bulletinName=" + bulletinName
				+ ", cxLevel=" + cxLevel + ", saId=" + saId + "]";
	}
	

}
