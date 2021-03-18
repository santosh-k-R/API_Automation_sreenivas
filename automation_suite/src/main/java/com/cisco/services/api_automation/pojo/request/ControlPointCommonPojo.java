package com.cisco.services.api_automation.pojo.request;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ControlPointCommonPojo {
   
        private String useCase;
        private String ccoId;
        private String customerId;
        private String header;
        private String headers;
        private String saId;
        private String vaId;
        private String solution;
        private String saAccountId;
        private String cscheader;

	public String getcscheader() {
		return cscheader;
	}

	public void setcscheader(String cscheader) {
		this.cscheader = cscheader;
	}

	public String getVaId() {
			return vaId;
		}

		public void setVaId(String vaId) {
			this.vaId = vaId;
		}

		public String getHeaders() {
			return headers;
		}

		public void setHeaders(String headers) {
			this.headers = headers;
		}


		public String getSaAccountId() {
			return saAccountId;
		}

		public void setSaAccountId(String saAccountId) {
			this.saAccountId = saAccountId;
		}

		public String getSolution() {
			return solution;
		}

		public void setSolution(String solution) {
			this.solution = solution;
		}

        public String getSaId() {
			return saId;
		}

		public void setSaId(String saId) {
			this.saId = saId;
		}

		public String getHeader() {
			return header;
		}

		public void setHeader(String header) {
			this.header = header;
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

        public void setUseCase(String useCase) {
            this.useCase = useCase;
        }

        public void setCcoId(String ccoId) {
            this.ccoId = ccoId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }
    
}
