package com.cisco.services.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OneIDUsersResponse {
	private static final Logger LOG = LoggerFactory.getLogger(OneIDUsersResponse.class);

	@JsonProperty("totalResults")
	private Long totalResults;
	@JsonProperty("Resources")
	private List<Resource> resources = null;

	@JsonProperty("totalResults")
	public Long getTotalResults() {
		return totalResults;
	}

	@JsonProperty("totalResults")
	public void setTotalResults(Long totalResults) {
		this.totalResults = totalResults;
	}

	@JsonProperty("Resources")
	public List<Resource> getResources() {
		return resources;
	}

	@JsonProperty("Resources")
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	@JsonIgnore
	public String getUserFullName() {
		String userFullName = null;

		if(totalResults > 0) {
			Name usersName = this.resources.get(0).getName();
			userFullName = (usersName != null) ? usersName.getGivenName() + " " + usersName.getFamilyName()  : null;
		}

		return userFullName;
	}

	@JsonIgnore
	public String getAccessLevel() {
		String accessLevel = null;

		if (totalResults > 0) {
			UrnCiscoOneidentitySchemas10COIUser coiuser = this.resources.get(0).getUrnCiscoOneidentitySchemas10COIUser();
			accessLevel = (coiuser != null) ? coiuser.getAccessLevel() : null;
		}

		return accessLevel;
	}

	@JsonIgnore
	public String getCompanyName() {
		String companyName = null;

		if (totalResults > 0) {
			UrnCiscoOneidentitySchemas10COIUser coiuser = this.resources.get(0).getUrnCiscoOneidentitySchemas10COIUser();
			companyName = (coiuser != null) ? coiuser.getOrganization() : null;
		}

		return companyName;
	}

	@JsonIgnore
	public String getJobTitle() {
		String jobTitle = null;

		if (totalResults > 0) {
			UrnCiscoOneidentitySchemas10COIUser coiuser = this.resources.get(0).getUrnCiscoOneidentitySchemas10COIUser();
			JobInfo jobInfo = (coiuser != null) ? coiuser.getJobInfo() : null;
			jobTitle = (jobInfo != null) ? jobInfo.getJobTitle() : null;
		}

		return jobTitle;
	}

	@JsonIgnore
	public String getUserEmail() {
		String userEmail = null;

		if (totalResults > 0) {
			userEmail = this.resources.get(0).getUserName();
		}

		return userEmail;
	}

	@JsonIgnore
	public String getUserPhoneNumber() {
		String userPhoneNumber = null;

		if (totalResults > 0) {
			List<PhoneNumber> phoneNumbers = this.resources.get(0).getPhoneNumbers();
			PhoneNumber primaryPhone = (phoneNumbers != null) ? (
					phoneNumbers.stream()
					.filter(phone -> phone.getPrimary() != null)
					.filter(PhoneNumber::getPrimary).findFirst()
					.get()
			) : null;

			userPhoneNumber = (primaryPhone != null) ? primaryPhone.getValue() : null;
		}

		return userPhoneNumber;
	}

	@JsonIgnore
	public String getCiscoContact() {
		return "TBD";
	}

	@JsonIgnore
	public String getCountry() {
		String country = null;

		if (totalResults > 0) {
			List<Address> phoneNumbers = this.resources.get(0).getAddresses();
			Address primaryAddress = (phoneNumbers != null) ? (
					phoneNumbers.stream()
							.filter(phone -> phone.getPrimary() != null)
							.filter(Address::getPrimary).findFirst()
							.get()
			) : null;

			country = (primaryAddress != null) ? primaryAddress.getCountry() : null;
		}

		return country;
	}

	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (Exception e) {
			LOG.error("Error while converting OneIDUsersResponse to JSON");
			e.printStackTrace();
			return super.toString();
		}
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class Resource {
	@JsonProperty("id")
	private String id;
	@JsonProperty("meta")
	private Meta meta;
	@JsonProperty("userName")
	private String userName;
	@JsonProperty("name")
	private Name name;
	@JsonProperty("preferredLanguage")
	private String preferredLanguage;
	@JsonProperty("active")
	private Boolean active;
	@JsonProperty("phoneNumbers")
	private List<PhoneNumber> phoneNumbers = null;
	@JsonProperty("addresses")
	private List<Address> addresses = null;
	@JsonProperty("urn:cisco:oneidentity:schemas:1.0:COIUser")
	private UrnCiscoOneidentitySchemas10COIUser urnCiscoOneidentitySchemas10COIUser;

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("meta")
	public Meta getMeta() {
		return meta;
	}

	@JsonProperty("meta")
	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	@JsonProperty("userName")
	public String getUserName() {
		return userName;
	}

	@JsonProperty("userName")
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty("name")
	public Name getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(Name name) {
		this.name = name;
	}

	@JsonProperty("preferredLanguage")
	public String getPreferredLanguage() {
		return preferredLanguage;
	}

	@JsonProperty("preferredLanguage")
	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

	@JsonProperty("active")
	public Boolean getActive() {
		return active;
	}

	@JsonProperty("active")
	public void setActive(Boolean active) {
		this.active = active;
	}

	@JsonProperty("phoneNumbers")
	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	@JsonProperty("phoneNumbers")
	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	@JsonProperty("addresses")
	public List<Address> getAddresses() {
		return addresses;
	}

	@JsonProperty("addresses")
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	@JsonProperty("urn:cisco:oneidentity:schemas:1.0:COIUser")
	public UrnCiscoOneidentitySchemas10COIUser getUrnCiscoOneidentitySchemas10COIUser() {
		return urnCiscoOneidentitySchemas10COIUser;
	}

	@JsonProperty("urn:cisco:oneidentity:schemas:1.0:COIUser")
	public void setUrnCiscoOneidentitySchemas10COIUser(UrnCiscoOneidentitySchemas10COIUser urnCiscoOneidentitySchemas10COIUser) {
		this.urnCiscoOneidentitySchemas10COIUser = urnCiscoOneidentitySchemas10COIUser;
	}
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class Address {

	@JsonProperty("streetAddress")
	private String streetAddress;
	@JsonProperty("locality")
	private String locality;
	@JsonProperty("region")
	private String region;
	@JsonProperty("postalCode")
	private String postalCode;
	@JsonProperty("country")
	private String country;
	@JsonProperty("type")
	private String type;
	@JsonProperty("primary")
	private Boolean primary;

	@JsonProperty("streetAddress")
	public String getStreetAddress() {
		return streetAddress;
	}

	@JsonProperty("streetAddress")
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	@JsonProperty("locality")
	public String getLocality() {
		return locality;
	}

	@JsonProperty("locality")
	public void setLocality(String locality) {
		this.locality = locality;
	}

	@JsonProperty("region")
	public String getRegion() {
		return region;
	}

	@JsonProperty("region")
	public void setRegion(String region) {
		this.region = region;
	}

	@JsonProperty("postalCode")
	public String getPostalCode() {
		return postalCode;
	}

	@JsonProperty("postalCode")
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@JsonProperty("country")
	public String getCountry() {
		return country;
	}

	@JsonProperty("country")
	public void setCountry(String country) {
		this.country = country;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("primary")
	public Boolean getPrimary() {
		return primary;
	}

	@JsonProperty("primary")
	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class ExternalIDPLink {

	@JsonProperty("source")
	private String source;
	@JsonProperty("value")
	private String value;

	@JsonProperty("source")
	public String getSource() {
		return source;
	}

	@JsonProperty("source")
	public void setSource(String source) {
		this.source = source;
	}

	@JsonProperty("value")
	public String getValue() {
		return value;
	}

	@JsonProperty("value")
	public void setValue(String value) {
		this.value = value;
	}
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class JobInfo {

	@JsonProperty("jobTitle")
	private String jobTitle;

	@JsonProperty("jobTitle")
	public String getJobTitle() {
		return jobTitle;
	}

	@JsonProperty("jobTitle")
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class Meta {
	@JsonProperty("resourceType")
	private String resourceType;
	@JsonProperty("created")
	private String created;
	@JsonProperty("lastModified")
	private String lastModified;

	@JsonProperty("resourceType")
	public String getResourceType() {
		return resourceType;
	}

	@JsonProperty("resourceType")
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	@JsonProperty("created")
	public String getCreated() {
		return created;
	}

	@JsonProperty("created")
	public void setCreated(String created) {
		this.created = created;
	}

	@JsonProperty("lastModified")
	public String getLastModified() {
		return lastModified;
	}

	@JsonProperty("lastModified")
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class Name {

	@JsonProperty("familyName")
	private String familyName;
	@JsonProperty("givenName")
	private String givenName;

	@JsonProperty("familyName")
	public String getFamilyName() {
		return familyName;
	}

	@JsonProperty("familyName")
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	@JsonProperty("givenName")
	public String getGivenName() {
		return givenName;
	}

	@JsonProperty("givenName")
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class PhoneNumber {

	@JsonProperty("value")
	private String value;
	@JsonProperty("display")
	private String display;
	@JsonProperty("primary")
	private Boolean primary;

	@JsonProperty("value")
	public String getValue() {
		return value;
	}

	@JsonProperty("value")
	public void setValue(String value) {
		this.value = value;
	}

	@JsonProperty("display")
	public String getDisplay() {
		return display;
	}

	@JsonProperty("display")
	public void setDisplay(String display) {
		this.display = display;
	}

	@JsonProperty("primary")
	public Boolean getPrimary() {
		return primary;
	}

	@JsonProperty("primary")
	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class UrnCiscoOneidentitySchemas10COIUser {

	@JsonProperty("accessLevel")
	private String accessLevel;
	@JsonProperty("crPartyDetailsDN")
	private String crPartyDetailsDN;
	@JsonProperty("externalIDPLink")
	private List<ExternalIDPLink> externalIDPLink = null;
	@JsonProperty("jobInfo")
	private JobInfo jobInfo;
	@JsonProperty("organization")
	private String organization;
	@JsonProperty("ownerUUID")
	private String ownerUUID;
	@JsonProperty("tenantUUID")
	private String tenantUUID;
	@JsonProperty("country")
	private String country;
	@JsonProperty("dataMergedSource")
	private List<String> dataMergedSource = null;
	@JsonProperty("userStatus")
	private List<Userstatus> userStatus = null;

	@JsonProperty("accessLevel")
	public String getAccessLevel() {
		return accessLevel;
	}

	@JsonProperty("accessLevel")
	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	@JsonProperty("crPartyDetailsDN")
	public String getCrPartyDetailsDN() {
		return crPartyDetailsDN;
	}

	@JsonProperty("crPartyDetailsDN")
	public void setCrPartyDetailsDN(String crPartyDetailsDN) {
		this.crPartyDetailsDN = crPartyDetailsDN;
	}

	@JsonProperty("externalIDPLink")
	public List<ExternalIDPLink> getExternalIDPLink() {
		return externalIDPLink;
	}

	@JsonProperty("externalIDPLink")
	public void setExternalIDPLink(List<ExternalIDPLink> externalIDPLink) {
		this.externalIDPLink = externalIDPLink;
	}

	@JsonProperty("jobInfo")
	public JobInfo getJobInfo() {
		return jobInfo;
	}

	@JsonProperty("jobInfo")
	public void setJobInfo(JobInfo jobInfo) {
		this.jobInfo = jobInfo;
	}

	@JsonProperty("organization")
	public String getOrganization() {
		return organization;
	}

	@JsonProperty("organization")
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	@JsonProperty("ownerUUID")
	public String getOwnerUUID() {
		return ownerUUID;
	}

	@JsonProperty("ownerUUID")
	public void setOwnerUUID(String ownerUUID) {
		this.ownerUUID = ownerUUID;
	}

	@JsonProperty("tenantUUID")
	public String getTenantUUID() {
		return tenantUUID;
	}

	@JsonProperty("tenantUUID")
	public void setTenantUUID(String tenantUUID) {
		this.tenantUUID = tenantUUID;
	}

	@JsonProperty("country")
	public String getCountry() {
		return country;
	}

	@JsonProperty("country")
	public void setCountry(String country) {
		this.country = country;
	}

	@JsonProperty("dataMergedSource")
	public List<String> getDataMergedSource() {
		return dataMergedSource;
	}

	@JsonProperty("dataMergedSource")
	public void setDataMergedSource(List<String> dataMergedSource) {
		this.dataMergedSource = dataMergedSource;
	}

	@JsonProperty("userStatus")
	public List<Userstatus> getUserStatus() {
		return userStatus;
	}

	@JsonProperty("userStatus")
	public void setUserStatus(List<Userstatus> userStatus) {
		this.userStatus = userStatus;
	}
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class Userstatus {

	@JsonProperty("source")
	private String source;
	@JsonProperty("description")
	private String description;
	@JsonProperty("status")
	private String status;
	@JsonProperty("statusChangeTimeEpoc")
	private Long statusChangeTimeEpoc;
	@JsonProperty("primaryAccountUUID")
	private String primaryAccountUUID;

	@JsonProperty("source")
	public String getSource() {
		return source;
	}

	@JsonProperty("source")
	public void setSource(String source) {
		this.source = source;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("statusChangeTimeEpoc")
	public Long getStatusChangeTimeEpoc() {
		return statusChangeTimeEpoc;
	}

	@JsonProperty("statusChangeTimeEpoc")
	public void setStatusChangeTimeEpoc(Long statusChangeTimeEpoc) {
		this.statusChangeTimeEpoc = statusChangeTimeEpoc;
	}

	@JsonProperty("primaryAccountUUID")
	public String getPrimaryAccountUUID() {
		return primaryAccountUUID;
	}

	@JsonProperty("primaryAccountUUID")
	public void setPrimaryAccountUUID(String primaryAccountUUID) {
		this.primaryAccountUUID = primaryAccountUUID;
	}
}
