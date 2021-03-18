package com.cisco.services.api_automation.pojo.response.compliance;

import java.util.List;

public class ViolationFiltersPOJO {

    private String status;
    private String message;
    public Data data;

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
        private int assetCount;
        private String customerId;
        private int policyViolationCount;
        private List<PolicyFilters> policyFilters;
        private List<SeverityFilters> severityFilters;

        public int getAssetCount() {
            return assetCount;
        }

        public void setAssetCount(int assetCount) {
            this.assetCount = assetCount;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public int getPolicyViolationCount() {
            return policyViolationCount;
        }

        public void setPolicyViolationCount(int policyViolationCount) {
            this.policyViolationCount = policyViolationCount;
        }

        public List<PolicyFilters> getPolicyFilters() {
            return policyFilters;
        }

        public void setPolicyFilters(List<PolicyFilters> policyFilters) {
            this.policyFilters = policyFilters;
        }

        public List<SeverityFilters> getSeverityFilters() {
            return severityFilters;
        }

        public void setSeverityFilters(List<SeverityFilters> severityFilters) {
            this.severityFilters = severityFilters;
        }


        public static class PolicyFilters {
            private String filter;
            private int value;
            private String label;

            public String getFilter() {
                return filter;
            }

            public void setFilter(String filter) {
                this.filter = filter;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }
        }

        public static class SeverityFilters {
            private String filter;
            private int value;
            private String label;

            public String getFilter() {
                return filter;
            }

            public void setFilter(String filter) {
                this.filter = filter;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

        }
    }

}
