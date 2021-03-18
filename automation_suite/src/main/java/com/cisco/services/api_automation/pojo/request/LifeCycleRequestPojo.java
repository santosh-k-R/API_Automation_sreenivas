package com.cisco.services.api_automation.pojo.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LifeCycleRequestPojo {
    private String name;
    private ArrayList<LifeCycleRequestPojo.data> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(ArrayList<LifeCycleRequestPojo.data> data) {
        this.data = data;
    }

    public ArrayList<LifeCycleRequestPojo.data> getData() {
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
        private String table;
        private boolean bookmark;
        private String accId;
        private String lifecycleCategory;
        private String pitStop;
        private String useCase;
        private String ccoId;
        private String customerId;

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
            this.ccoId = ccoId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }
    }
}
