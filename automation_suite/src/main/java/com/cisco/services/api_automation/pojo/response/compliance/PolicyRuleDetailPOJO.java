package com.cisco.services.api_automation.pojo.response.compliance;

import java.util.List;

public class PolicyRuleDetailPOJO {
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


	public static class Data {
		private Rule rule;
		private Policy policy;
		private String customerId;
		private DeviceFilterDetails deviceFilterDetails;
		
		
		public Rule getRule() {
			return rule;
		}

		public void setRule(Rule rule) {
			this.rule = rule;
		}

		public Policy getPolicy() {
			return policy;
		}

		public void setPolicy(Policy policy) {
			this.policy = policy;
		}

		public String getCustomerId() {
			return customerId;
		}

		public void setCustomerId(String customerId) {
			this.customerId = customerId;
		}

		public DeviceFilterDetails getDeviceFilterDetails() {
			return deviceFilterDetails;
		}

		public void setDeviceFilterDetails(DeviceFilterDetails deviceFilterDetails) {
			this.deviceFilterDetails = deviceFilterDetails;
		}

		public static class Rule {
			private String name;
			private String desc;
			private String ruleId;
			
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getDesc() {
				return desc;
			}
			public void setDesc(String desc) {
				this.desc = desc;
			}
			public String getRuleId() {
				return ruleId;
			}
			public void setRuleId(String ruleId) {
				this.ruleId = ruleId;
			}
			
			
		}
		
		public static class Policy {
			private String desc;
			private String name;
			private String policyId;
			public String getDesc() {
				return desc;
			}
			public void setDesc(String desc) {
				this.desc = desc;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getPolicyId() {
				return policyId;
			}
			public void setPolicyId(String policyId) {
				this.policyId = policyId;
			}
			
		}
		
		public static class DeviceFilterDetails{
			
			private List<String> productFamily;
			private List<String> productModel;
			public List<String> getProductFamily() {
				return productFamily;
			}
			public void setProductFamily(List<String> productFamily) {
				this.productFamily = productFamily;
			}
			public List<String> getProductModel() {
				return productModel;
			}
			public void setProductModel(List<String> productModel) {
				this.productModel = productModel;
			}
			
			
		}
		
	}

}
