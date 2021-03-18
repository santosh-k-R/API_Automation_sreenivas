package com.cisco.services.api_automation.pojo.response.compliance;

import java.util.List;

public class AssetDetailsFilterPOJO {
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
        private List<String> policyGroupName;
        private List<String> ruleHighSeverity;
        private String customerId;
        private String serialNumber;

        public List<String> getPolicyGroupName() {
            return policyGroupName;
        }

        public void setPolicyGroupName(List<String> policyGroupName) {
            this.policyGroupName = policyGroupName;
        }

        public List<String> getRuleHighSeverity() {
            return ruleHighSeverity;
        }

        public void setRuleHighSeverity(List<String> ruleHighSeverity) {
            this.ruleHighSeverity = ruleHighSeverity;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

    }

}
