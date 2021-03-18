package com.cisco.services.api_automation.pojo.response.controlpoint;

import java.util.List;
import java.util.Map;

public class ControlPointPojo {
    private String totalHits;
    private String count;
    private String dismissed;
    private String unread;
    private List<Items> items;

    public String getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(String totalHits) {
        this.totalHits = totalHits;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> itemsList) {
        this.items = itemsList;
    }

    public String getDismissed() {
        return dismissed;
    }

    public void setDismissed(String dismissed) {
        this.dismissed = dismissed;
    }

    public String getUnread() {
        return unread;
    }

    public void setUnread(String unread) {
        this.unread = unread;
    }

    public static class Items {
        public Items() {
        }

        private String source;
        private String target;
        private String category;
        private String impact;
        private String priority;
        private String message;
        private String channel;
        private String startTimestamp;
        private String endTimestamp;
        private String dismissable;
        private String notificationId;
        private String visited;
        private String dismissed;
        private String deleted;
        private Map<String,String> context;
        public String getSource() {
            return source;
        }

        public Map<String, String> getContext() {
            return context;
        }

        public void setContext(Map<String, String> context) {
            this.context = context;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getImpact() {
            return impact;
        }

        public void setImpact(String impact) {
            this.impact = impact;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getStartTimestamp() {
            return startTimestamp;
        }

        public void setStartTimestamp(String startTimestamp) {
            this.startTimestamp = startTimestamp;
        }

        public String getEndTimestamp() {
            return endTimestamp;
        }

        public void setEndTimestamp(String endTimestamp) {
            this.endTimestamp = endTimestamp;
        }

        public String getDismissable() {
            return dismissable;
        }

        public void setDismissable(String dismissable) {
            this.dismissable = dismissable;
        }

        public String getNotificationId() {
            return notificationId;
        }

        public void setNotificationId(String notificationId) {
            this.notificationId = notificationId;
        }

        public String getVisited() {
            return visited;
        }

        public void setVisited(String visited) {
            this.visited = visited;
        }

        public String getDismissed() {
            return dismissed;
        }

        public void setDismissed(String dismissed) {
            this.dismissed = dismissed;
        }

        public String getDeleted() {
            return deleted;
        }

        public void setDeleted(String deleted) {
            this.deleted = deleted;
        }

        private static class context {
            public context() {
            }

            private String entityTitle;
            private String closedDate;
            private String assetId;
            private String customerId;
            private String entityId;
            private String sessionId;
            private String partnerId;
            private String presenterName;
            private String assetType;

            public String getEntityTitle() {
                return entityTitle;
            }

            public void setEntityTitle(String entityTitle) {
                this.entityTitle = entityTitle;
            }

            public String getClosedDate() {
                return closedDate;
            }

            public void setClosedDate(String closedDate) {
                this.closedDate = closedDate;
            }

            public String getAssetId() {
                return assetId;
            }

            public void setAssetId(String assetId) {
                this.assetId = assetId;
            }

            public String getCustomerId() {
                return customerId;
            }

            public void setCustomerId(String customerId) {
                this.customerId = customerId;
            }

            public String getEntityId() {
                return entityId;
            }

            public void setEntityId(String entityId) {
                this.entityId = entityId;
            }

            public String getSessionId() {
                return sessionId;
            }

            public void setSessionId(String sessionId) {
                this.sessionId = sessionId;
            }

            public String getPartnerId() {
                return partnerId;
            }

            public void setPartnerId(String partnerId) {
                this.partnerId = partnerId;
            }

            public String getPresenterName() {
                return presenterName;
            }

            public void setPresenterName(String presenterName) {
                this.presenterName = presenterName;
            }

            public String getAssetType() {
                return assetType;
            }

            public void setAssetType(String assetType) {
                this.assetType = assetType;
            }
        }
    }
}
