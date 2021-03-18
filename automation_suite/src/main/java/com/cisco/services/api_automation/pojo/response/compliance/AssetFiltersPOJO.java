package com.cisco.services.api_automation.pojo.response.compliance;

import java.util.List;

public class AssetFiltersPOJO {
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
        private List<SeverityList> severityList;
        private String customerId;

        public List<SeverityList> getSeverityList() {
            return severityList;
        }

        public void setSeverityList(List<SeverityList> severityList) {
            this.severityList = severityList;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCusstomerId(String cusstomerId) {
            this.customerId = cusstomerId;
        }

        public static class SeverityList {
            private String filter;
            private float percentage;
            private String label;
            private int value;

            public String getFilter() {
                return filter;
            }

            public void setFilter(String filter) {
                this.filter = filter;
            }

            public float getPercentage() {
                return percentage;
            }

            public void setPercentage(float percentage) {
                this.percentage = percentage;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }
        }
    }
}
