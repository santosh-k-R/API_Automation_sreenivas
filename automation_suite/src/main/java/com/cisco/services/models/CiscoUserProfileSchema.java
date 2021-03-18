package com.cisco.services.models;

import org.apache.commons.lang3.StringUtils;

public class CiscoUserProfileSchema {
	private String companyName;
	private String jobTitle;
	private String accessLevel;
	private String userEmail;
	private String userPhoneNumber;
	private String ciscoContact;
	private String ccoId;
	private String country;
	private String userFullName;

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getAccessLevel() { return accessLevel; }

	public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public String getCiscoContact() {
		return ciscoContact;
	}

	public void setCiscoContact(String ciscoContact) {
		this.ciscoContact = ciscoContact;
	}

	public String getCcoId() {
		return ccoId;
	}

	public void setCcoId(String ccoId) {
		this.ccoId = ccoId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean isValid() {
		return StringUtils.isNotBlank(this.ccoId);
	}

	@Override
	public String toString() {
		return "CiscoUserProfileSchema {" + "companyName=" + companyName  + ", jobTitle=" + jobTitle + ", userEmail="
				+ userEmail + ", userPhoneNumber=" + userPhoneNumber + ", ciscoContact=" + ciscoContact + ", ccoId="
				+ ccoId + ", country=" + country + ", userFullName=" + userFullName + '}';
	}
}
