package com.cisco.services.api_automation.utils.results;

import java.util.Objects;

public class ResultBody {

    private String id;
    private String title;
    private TestResults.Results status;

    public ResultBody(String id, TestResults.Results status, String title) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TestResults.Results getStatus() {
        return status;
    }

    public void setStatus(TestResults.Results status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultBody that = (ResultBody) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
