package com.cisco.services.api_automation.pojo.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ControlPointRequestPojo {
    private String name;
    private ArrayList<ControlPointRequestPojo.data> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(ArrayList<ControlPointRequestPojo.data> data) {
        this.data = data;
    }

    public ArrayList<ControlPointRequestPojo.data> getData() {
        return data;
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class data {
        public data() {
        }


        private String endPoint;
        private String body;
        private String params;
        private String esQuery;
       	private String esQueryAfter;
        private String table;
        private String tableAfter;
       	private boolean bookmark;
       	private String pathParams;	
        private String accId;
        private String lifecycleCategory;
        private String pitStop;
        private String useCase;
        private String ccoId;
        private String customerId;
        private String cavId;
        private String header;
        private String headers;
        private String responseBodyStatus;
        private String responseBody;
        private String saId;
        private String datacenter;
        private String applianceId;
        private String remoteNodeId;
        private String remoteNodeName;
        private String ieSetupCompleted;
        private String registrationStatus;
        private String pageNumber;
        private String rowsPerPage;
        private String solution;
        private String esQueryTwo;
        private String tableTwo;
        private String endPointTwo;
		private String endPointThree;
        private String esQueryThree;
        private String tableThree;
        private String bodyTwo;
		private String scheduleId;
		private String policySchedule;
		private int responseCode;
		private String responseMessage;
		private String testType;
		private String showPastPartners;
		private String  begeoId;
		private String modifiedAfter;
		private String assign;
		private String tokenType;
		private String comment;
		private String searchTerm;


		public String getComment() { return comment; }
		public void setComment(String comment) { this.comment = comment; }

		public String getTokenType() {
			return tokenType;
		}
		public void setTokenType(String tokenType) {
			this.tokenType = tokenType;
		}

		public String getAssign() { return assign; }
		public void setAssign(String assign) { this.assign = assign; }

		public String getSearchTerm() { return searchTerm;	}
		public void setSearchTerm(String searchTerm) {	this.searchTerm = searchTerm;	}

		public String getModifiedAfter() { return modifiedAfter; }
		public void setModifiedAfter(String modifiedAfter) { this.modifiedAfter = modifiedAfter; }

		public String getBegeoId() { return begeoId; }
		public void setBegeoId(String begeoId) { this.begeoId = begeoId; }

		public String getShowPastPartners() { return showPastPartners;	}
		public void setShowPastPartners(String showPastPartners) { this.showPastPartners = showPastPartners; }

		public String getResponseMessage() {
			return responseMessage;
		}

		public void setResponseMessage(String responseMessage) {
			this.responseMessage = responseMessage;
		}

		public int getResponseCode() {
			return responseCode;
		}

		public void setResponseCode(int responseCode) {
			this.responseCode = responseCode;
		}

		public String getTestType() {
			return testType;
		}

		public void setTestType(String testType) {
			this.testType = testType;
		}


		public String getPolicySchedule() {
			return policySchedule;
		}

		public void setPolicySchedule(String policySchedule) {
			this.policySchedule = policySchedule;
		}


		public void setScheduleId(String scheduleId) {
			this.scheduleId = scheduleId;
		}

		public String getScheduleId() {
			return scheduleId;
		}

        public String getHeaders() {
			return headers;
		}

		public void setHeaders(String headers) {
			this.headers = headers;
		}

		public String getEsQueryThree() {
			return esQueryThree;
		}

		public void setEsQueryThree(String esQueryThree) {
			this.esQueryThree = esQueryThree;
		}

		public String getTableThree() {
			return tableThree;
		}

		public void setTableThree(String tableThree) {
			this.tableThree = tableThree;
		}

		public String getEndPointTwo() {
			return endPointTwo;
		}

		public void setEndPointTwo(String endPointTwo) {
			this.endPointTwo = endPointTwo;
		}

		public String getEndPointThree() {
			return endPointThree;
		}

		public void setEndPointThree(String endPointThree) {
			this.endPointThree = endPointThree;
		}

		public String getBodyTwo() {
			return bodyTwo;
		}

		public void setBodyTwo(String bodyTwo) {
			this.bodyTwo = bodyTwo;
		}

		public String getTableTwo() {
			return tableTwo;
		}

		public void setTableTwo(String tableTwo) {
			this.tableTwo = tableTwo;
		}

		public String getEsQueryTwo() {
			return esQueryTwo;
		}

		public void setEsQueryTwo(String esQueryTwo) {
			this.esQueryTwo = esQueryTwo;
		}

		public String getSolution() {
			return solution;
		}

		public void setSolution(String solution) {
			this.solution = solution;
		}

		public String getRowsPerPage() {
			return rowsPerPage;
		}

		public void setRowsPerPage(String rowsPerPage) {
			this.rowsPerPage = rowsPerPage;
		}

		public String getPageNumber() {
			return pageNumber;
		}

		public void setPageNumber(String pageNumber) {
			this.pageNumber = pageNumber;
		}

		public String getPathParams() {
			return pathParams;
		}

		public void setPathParams(String pathParams) {
			this.pathParams = pathParams;
		}

		public String getTableAfter() {
			return tableAfter;
		}

		public void setTableAfter(String tableAfter) {
			this.tableAfter = tableAfter;
		}
		
        public String getEsQueryAfter() {
			return esQueryAfter;
		}

		public void setEsQueryAfter(String esQueryAfter) {
			this.esQueryAfter = esQueryAfter;
		}

        public String getSaId() {
			return saId;
		}

		public void setSaId(String saId) {
			this.saId = saId;
		}

		public String getDatacenter() {
			return datacenter;
		}

		public void setDatacenter(String datacenter) {
			this.datacenter = datacenter;
		}

		public String getApplianceId() {
			return applianceId;
		}

		public void setApplianceId(String applianceId) {
			this.applianceId = applianceId;
		}

		public String getRemoteNodeId() {
			return remoteNodeId;
		}

		public void setRemoteNodeId(String remoteNodeId) {
			this.remoteNodeId = remoteNodeId;
		}

		public String getRemoteNodeName() {
			return remoteNodeName;
		}

		public void setRemoteNodeName(String remoteNodeName) {
			this.remoteNodeName = remoteNodeName;
		}

        public String getIeSetupCompleted() {
			return ieSetupCompleted;
		}

		public void setIeSetupCompleted(String ieSetupCompleted) {
			this.ieSetupCompleted = ieSetupCompleted;
		}

		public String getRegistrationStatus() {
			return registrationStatus;
		}

		public void setRegistrationStatus(String registrationStatus) {
			this.registrationStatus = registrationStatus;
		}

        public String getResponseBodyStatus() {
			return responseBodyStatus;
		}

		public void setResponseBodyStatus(String responseBodyStatus) {
			this.responseBodyStatus = responseBodyStatus;
		}

		public String getResponseBody() {
			return responseBody;
		}

		public void setResponseBody(String responseBody) {
			this.responseBody = responseBody;
		}

		public String getHeader() {
			return header;
		}

		public void setHeader(String header) {
			this.header = header;
		}

		public boolean isBookmark() {
            return bookmark;
        }

        public void setBookmark(boolean bookmark) {
            this.bookmark = bookmark;
        }
        public String getEndPoint() {
            return endPoint;
        }

        public String getBody() {
            return body;
        }

        public String getParams() {
            return params;
        }

        public String getEsQuery() {
            return esQuery;
        }

        public String getTable() {
            return table;
        }

        public String getAccId() {
            return accId;
        }

        public String getLifecycleCategory() {
            return lifecycleCategory;
        }

        public String getPitStop() {
            return pitStop;
        }

        public String getUseCase() {
            return useCase;
        }

        public String getCcoId() {
            return ccoId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setEndPoint(String endPoint) {
            this.endPoint = endPoint;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public void setParams(String params) {
            this.params = params;
        }

        public void setEsQuery(String esQuery) {
            this.esQuery = esQuery;
        }

        public void setTable(String table) {
            this.table = table;
        }

        public void setAccId(String accId) {
            this.accId = accId;
        }

        public void setLifecycleCategory(String lifecycleCategory) {
            this.lifecycleCategory = lifecycleCategory;
        }

        public void setPitStop(String pitStop) {
            this.pitStop = pitStop;
        }

        public void setUseCase(String useCase) {
            this.useCase = useCase;
        }

        public void setCcoId(String ccoId) {
            this.ccoId = ccoId;}

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }
		public void setcavId(String cavId) {
			this.cavId = cavId;}
    }
}
