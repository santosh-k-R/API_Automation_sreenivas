package com.cisco.services.api_automation.pojo.response.lifecycle;

import java.util.List;

public class AccGetPojo {
    private String solution;
    private String usecase;
    private String pitstop;
    private List<AccGetPojo.Items> Items;

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getUsecase() {
        return usecase;
    }

    public void setUsecase(String usecase) {
        this.usecase = usecase;
    }

    public String getPitstop() {
        return pitstop;
    }

    public void setPitstop(String pitstop) {
        this.pitstop = pitstop;
    }

    public List<AccGetPojo.Items> getItems() {
        return Items;
    }

    public void setItems(List<AccGetPojo.Items> items) {
        Items = items;
    }

    public static class Items {
        public Items() {
        }

        private String title;
        private String description;
        private String assetId;
        private String bookmark;
        private String url;
        private String requestId;
        private String technologyArea;
        private String status;
        private String accId;
        private String datasheetURL;
        private FeedbackInfo feedbackInfo;

        public ProviderInfo getProviderInfo() {
            return providerInfo;
        }

        public void setProviderInfo(ProviderInfo providerInfo) {
            this.providerInfo = providerInfo;
        }

        private ProviderInfo providerInfo;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAssetId() {
            return assetId;
        }

        public void setAssetId(String assetId) {
            this.assetId = assetId;
        }

        public String getBookmark() {
            return bookmark;
        }

        public void setBookmark(String bookmark) {
            this.bookmark = bookmark;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public String getTechnologyArea() {
            return technologyArea;
        }

        public void setTechnologyArea(String technologyArea) {
            this.technologyArea = technologyArea;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAccId() {
            return accId;
        }

        public void setAccId(String accId) {
            this.accId = accId;
        }

        public String getDatasheetURL() {
            return datasheetURL;
        }

        public void setDatasheetURL(String datasheetURL) {
            this.datasheetURL = datasheetURL;
        }

        public FeedbackInfo getFeedbackInfo() {
            return feedbackInfo;
        }

        public void setFeedbackInfo(FeedbackInfo feedbackInfo) {
            this.feedbackInfo = feedbackInfo;
        }

        public static class FeedbackInfo {
            private boolean available;
            private String feedbackId;
            private String sessionId;
            private String transactionId;
            private String presenterName;
            private String closedDate;
            private String thumbs;
            private String requestedDate;

            public boolean getAvailable() {
                return available;
            }

            public void setAvailable(boolean available) {
                this.available = available;
            }

            public String getFeedbackId() {
                return feedbackId;
            }

            public void setFeedbackId(String feedbackId) {
                this.feedbackId = feedbackId;
            }

            public String getSessionId() {
                return sessionId;
            }

            public void setSessionId(String sessionId) {
                this.sessionId = sessionId;
            }

            public String getTransactionId() {
                return transactionId;
            }

            public void setTransactionId(String transactionId) {
                this.transactionId = transactionId;
            }

            public String getPresenterName() {
                return presenterName;
            }

            public void setPresenterName(String presenterName) {
                this.presenterName = presenterName;
            }

            public String getClosedDate() {
                return closedDate;
            }

            public void setClosedDate(String closedDate) {
                this.closedDate = closedDate;
            }

            public String getThumbs() {
                return thumbs;
            }

            public void setThumbs(String thumbs) {
                this.thumbs = thumbs;
            }

            public String getRequestedDate() {
                return requestedDate;
            }

            public void setRequestedDate(String requestedDate) {
                this.requestedDate = requestedDate;
            }
        }

        public static class ProviderInfo {
            private String logoURL,name,id;

            public String getLogoURL() {
                return logoURL;
            }

            public void setLogoURL(String logoURL) {
                this.logoURL = logoURL;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }


}
