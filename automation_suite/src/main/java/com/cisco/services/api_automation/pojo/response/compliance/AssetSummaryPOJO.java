package com.cisco.services.api_automation.pojo.response.compliance;

import java.util.List;

public class AssetSummaryPOJO {
    private String status;

    private String message;

    private Data data;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }

    public static class Data {
        private List<AssetList> assetList;

        private int totalViolatedDevices;

        private int totalViolationCount;

        private String customerId;

        private int totalCount;

        public void setAssetList(List<AssetList> assetList) {
            this.assetList = assetList;
        }

        public List<AssetList> getAssetList() {
            return this.assetList;
        }

        public void setTotalViolatedDevices(int totalViolatedDevices) {
            this.totalViolatedDevices = totalViolatedDevices;
        }

        public int getTotalViolatedDevices() {
            return this.totalViolatedDevices;
        }

        public void setTotalViolationCount(int totalViolationCount) {
            this.totalViolationCount = totalViolationCount;
        }

        public int getTotalViolationCount() {
            return this.totalViolationCount;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCustomerId() {
            return this.customerId;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalCount() {
            return this.totalCount;
        }

        public static class AssetList {
            private String mgmtSystemId;

            private String mgmtSystemAddr;

            private String mgmtSystemHostname;

            private String mgmtSystemType;

            private String deviceId;

            private String hostName;

            private String ipAddress;

            private String lastScan;

            private String serialNumber;

            private String osType;

            private String osVersion;

            private int violationCount;

            private String ruleSeverity;

            private String ruleSeverityId;

            private boolean inProgress;

            private List<String> assetGroupRoles;

            public void setMgmtSystemId(String mgmtSystemId) {
                this.mgmtSystemId = mgmtSystemId;
            }

            public String getMgmtSystemId() {
                return this.mgmtSystemId;
            }

            public void setMgmtSystemAddr(String mgmtSystemAddr) {
                this.mgmtSystemAddr = mgmtSystemAddr;
            }

            public String getMgmtSystemAddr() {
                return this.mgmtSystemAddr;
            }

            public void setMgmtSystemHostname(String mgmtSystemHostname) {
                this.mgmtSystemHostname = mgmtSystemHostname;
            }

            public String getMgmtSystemHostname() {
                return this.mgmtSystemHostname;
            }

            public void setMgmtSystemType(String mgmtSystemType) {
                this.mgmtSystemType = mgmtSystemType;
            }

            public String getMgmtSystemType() {
                return this.mgmtSystemType;
            }

            public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
            }

            public String getDeviceId() {
                return this.deviceId;
            }

            public void setHostName(String hostName) {
                this.hostName = hostName;
            }

            public String getHostName() {
                return this.hostName;
            }

            public void setIpAddress(String ipAddress) {
                this.ipAddress = ipAddress;
            }

            public String getIpAddress() {
                return this.ipAddress;
            }

            public void setLastScan(String lastScan) {
                this.lastScan = lastScan;
            }

            public String getLastScan() {
                return this.lastScan;
            }

            public void setSerialNumber(String serialNumber) {
                this.serialNumber = serialNumber;
            }

            public String getSerialNumber() {
                return this.serialNumber;
            }

            public void setOsType(String osType) {
                this.osType = osType;
            }

            public String getOsType() {
                return this.osType;
            }

            public void setOsVersion(String osVersion) {
                this.osVersion = osVersion;
            }

            public String getOsVersion() {
                return this.osVersion;
            }

            public void setViolationCount(int violationCount) {
                this.violationCount = violationCount;
            }

            public int getViolationCount() {
                return this.violationCount;
            }

            public void setRuleSeverity(String ruleSeverity) {
                this.ruleSeverity = ruleSeverity;
            }

            public String getRuleSeverity() {
                return this.ruleSeverity;
            }

            public void setRuleSeverityId(String ruleSeverityId) {
                this.ruleSeverityId = ruleSeverityId;
            }

            public String getRuleSeverityId() {
                return this.ruleSeverityId;
            }

            public void setInProgress(boolean inProgress) {
                this.inProgress = inProgress;
            }

            public boolean getInProgress() {
                return this.inProgress;
            }

            public void setAssetGroupRoles(List<String> assetGroupRoles) {
                this.assetGroupRoles = assetGroupRoles;
            }

            public List<String> getAssetGroupRoles() {
                return this.assetGroupRoles;
            }
        }
    }


}
