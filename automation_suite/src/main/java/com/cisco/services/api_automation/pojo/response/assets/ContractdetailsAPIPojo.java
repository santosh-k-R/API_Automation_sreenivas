package com.cisco.services.api_automation.pojo.response.assets;

public class ContractdetailsAPIPojo {
	String customerId;
	String contractNumber;
	String serialNumber;
	String cxLevel;
	String saId;
	String contractStatus;
	String contractStartDate;
	String contractEndDate;
	String serviceProgram;
	String serviceLevel;
	String billtoSiteId;
	String billtoSiteName;
	String billtoAddressLine1;
	String billtoAddressLine2;
	String billtoAddressLine3;
	String billtoAddressLine4;
	String billtoCity;
	String billtoState;
	String billtoPostalCode;
	String billtoProvince;
	String billtoCountry;
	String billToGuName;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = String.valueOf(contractNumber);
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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
	public String getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	public String getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public String getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public String getServiceProgram() {
		return serviceProgram;
	}
	public void setServiceProgram(String serviceProgram) {
		this.serviceProgram = serviceProgram;
	}
	public String getServiceLevel() {
		return serviceLevel;
	}
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	public String getBilltoSiteId() {
		return billtoSiteId;
	}
	public void setBilltoSiteId(String billtoSiteId) {
		this.billtoSiteId = billtoSiteId;
	}
	public String getBilltoSiteName() {
		return billtoSiteName;
	}
	public void setBilltoSiteName(String billtoSiteName) {
		this.billtoSiteName = billtoSiteName;
	}
	public String getBilltoAddressLine1() {
		return billtoAddressLine1;
	}
	public void setBilltoAddressLine1(String billtoAddressLine1) {
		if(billtoAddressLine1==null)
			this.billtoAddressLine1 = "";
		else
			this.billtoAddressLine1 = String.valueOf(billtoAddressLine1);
	}
	public String getBilltoAddressLine2() {
		return billtoAddressLine2;
	}
	public void setBilltoAddressLine2(String billtoAddressLine2) {
		if(billtoAddressLine2==null)
			this.billtoAddressLine2="";
		else
			this.billtoAddressLine2 = String.valueOf(billtoAddressLine2);
	}
	public String getBilltoAddressLine3() {
		return billtoAddressLine3;
	}
	public void setBilltoAddressLine3(String billtoAddressLine3) {
		this.billtoAddressLine3 = billtoAddressLine3;
	}
	public String getBilltoAddressLine4() {
		return billtoAddressLine4;
	}
	public void setBilltoAddressLine4(String billtoAddressLine4) {
		this.billtoAddressLine4 = billtoAddressLine4;
	}
	public String getBilltoCity() {
		return billtoCity;
	}
	public void setBilltoCity(String billtoCity) {
		this.billtoCity = billtoCity;
	}
	public String getBilltoState() {
		return billtoState;
	}
	public void setBilltoState(String billtoState) {
		this.billtoState = billtoState;
	}
	public String getBilltoPostalCode() {
		return billtoPostalCode;
	}
	public void setBilltoPostalCode(String billtoPostalCode) {
		this.billtoPostalCode = billtoPostalCode;
	}
	public String getBilltoProvince() {
		return billtoProvince;
	}
	public void setBilltoProvince(String billtoProvince) {
		this.billtoProvince = billtoProvince;
	}
	public String getBilltoCountry() {
		return billtoCountry;
	}
	public void setBilltoCountry(String billtoCountry) {
		this.billtoCountry = billtoCountry;
	}
	public String getBillToGuName() {
		return billToGuName;
	}
	public void setBillToGuName(String billToGuName) {
		this.billToGuName = billToGuName;
	}
	@Override
	public String toString() {
		return "ContractdetailsAPIPojo [customerId=" + customerId + ", contractNumber=" + contractNumber
				+ ", serialNumber=" + serialNumber + ", cxLevel=" + cxLevel + ", saId=" + saId + ", contractStatus="
				+ contractStatus + ", contractStartDate=" + contractStartDate + ", contractEndDate=" + contractEndDate
				+ ", serviceProgram=" + serviceProgram + ", serviceLevel=" + serviceLevel + ", billtoSiteId="
				+ billtoSiteId + ", billtoSiteName=" + billtoSiteName + ", billtoAddressLine1=" + billtoAddressLine1
				+ ", billtoAddressLine2=" + billtoAddressLine2 + ", billtoAddressLine3=" + billtoAddressLine3
				+ ", billtoAddressLine4=" + billtoAddressLine4 + ", billtoCity=" + billtoCity + ", billtoState="
				+ billtoState + ", billtoPostalCode=" + billtoPostalCode + ", billtoProvince=" + billtoProvince
				+ ", billtoCountry=" + billtoCountry + ", billToGuName=" + billToGuName + "]";
	}
	
	
	


}
