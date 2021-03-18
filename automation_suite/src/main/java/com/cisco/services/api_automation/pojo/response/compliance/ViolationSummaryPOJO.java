package com.cisco.services.api_automation.pojo.response.compliance;

import java.util.List;

public class ViolationSummaryPOJO {

	private String status;
	private String message;
	private Data data;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public static class Data{
		private int totalCount;
		private List<Summary> summary;
		private int impactedAssetCount;
		private int violationCount;
		private String customerId;		
		
		public int getTotalCount() {
			return totalCount;
		}

		public void setTotalCount(int totalCount) {
			this.totalCount = totalCount;
		}


		public List<Summary> getSummary() {
			return summary;
		}


		public void setSummary(List<Summary> summary) {
			this.summary = summary;
		}


		public int getImpactedAssetCount() {
			return impactedAssetCount;
		}

		public void setImpactedAssetCount(int impactedAssetCount) {
			this.impactedAssetCount = impactedAssetCount;
		}

		public int getViolationCount() {
			return violationCount;
		}

		public void setViolationCount(int violationCount) {
			this.violationCount = violationCount;
		}

		public String getCustomerId() {
			return customerId;
		}

		public void setCustomerId(String customerId) {
			this.customerId = customerId;
		}




		public static class Summary{
			private String mgmtSystemId;
			private String mgmtSystemAddr;
			private String mgmtSystemHostname;
			private String mgmtSystemType;
			private String ruleSeverity;
			private String ruleSeverityId;
			private String policyGroupId;
			private String policyGroupName;
			private String policyId;
			private String ruleId;
			private String policyName;
			private String ruleTitle;
			private int violationCount;
			private int impactedAssetCount;
			private String policyCategory;
			
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
			public String getRuleSeverity() {
				return ruleSeverity;
			}
			public void setRuleSeverity(String ruleSeverity) {
				this.ruleSeverity = ruleSeverity;
			}
			public String getRuleSeverityId() {
				return ruleSeverityId;
			}
			public void setRuleSeverityId(String ruleSeverityId) {
				this.ruleSeverityId = ruleSeverityId;
			}
			public String getPolicyGroupId() {
				return policyGroupId;
			}
			public void setPolicyGroupId(String policyGroupId) {
				this.policyGroupId = policyGroupId;
			}
			public String getPolicyGroupName() {
				return policyGroupName;
			}
			public void setPolicyGroupName(String policyGroupName) {
				this.policyGroupName = policyGroupName;
			}
			public String getPolicyId() {
				return policyId;
			}
			public void setPolicyId(String policyId) {
				this.policyId = policyId;
			}
			public String getRuleId() {
				return ruleId;
			}
			public void setRuleId(String ruleId) {
				this.ruleId = ruleId;
			}
			public String getPolicyName() {
				return policyName;
			}
			public void setPolicyName(String policyName) {
				this.policyName = policyName;
			}
			public String getRuleTitle() {
				return ruleTitle;
			}
			public void setRuleTitle(String ruleTitle) {
				this.ruleTitle = ruleTitle;
			}
			public int getViolationCount() {
				return violationCount;
			}
			public void setViolationCount(int violationCount) {
				this.violationCount = violationCount;
			}
			public int getImpactedAssetCount() {
				return impactedAssetCount;
			}
			public void setImpactedAssetCount(int impactedAssetCount) {
				this.impactedAssetCount = impactedAssetCount;
			}
			public String getPolicyCategory() {
				return policyCategory;
			}
			public void setPolicyCategory(String policyCategory) {
				this.policyCategory = policyCategory;
			}
			
		}
	}
}
