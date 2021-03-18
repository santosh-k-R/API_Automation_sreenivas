package com.cisco.services.api_automation.pojo.response.architecturereview;

import java.util.List;

public class BPRulesDetailsPojo {
	
	    private String severity;

	    private String bpRuleTitle;

	    private String exceptions;

	    private String correctiveActions;

	    private String recommendations;

	    private List<String> deviceIpsWithExceptions;

	    private String softwareType;

	    private String description;
	   
	    private String example;


	    public void setSeverity(String severity){
	        this.severity = severity;
	    }
	    public String getSeverity(){
	        return this.severity;
	    }
	    public void setBpRuleTitle(String bpRuleTitle){
	        this.bpRuleTitle = bpRuleTitle;
	    }
	    public String getBpRuleTitle(){
	        return this.bpRuleTitle;
	    }
	    public void setExceptions(String exceptions){
	        this.exceptions = exceptions;
	    }
	    public String getExceptions(){
	        return this.exceptions;
	    }
	    public void setCorrectiveActions(String correctiveActions){
	        this.correctiveActions = correctiveActions;
	    }
	    public String getCorrectiveActions(){
	        return this.correctiveActions;
	    }
	    public void setRecommendations(String recommendations){
	        this.recommendations = recommendations;
	    }
	    public String getRecommendations(){
	        return this.recommendations;
	    }
	    public void setDeviceIpsWithExceptions(List<String> deviceIpsWithExceptions){
	        this.deviceIpsWithExceptions = deviceIpsWithExceptions;
	    }
	    public List<String> getDeviceIpsWithExceptions(){
	        return this.deviceIpsWithExceptions;
	    }
	    public void setSoftwareType(String softwareType){
	        this.softwareType = softwareType;
	    }
	    public String getSoftwareType(){
	        return this.softwareType;
	    }
	    public void setDescription(String description){
	        this.description = description;
	    }
	    public String getDescription(){
	        return this.description;
	    }
	    public void setExample(String example){
	        this.example = example;
	    }
	    public String getExample(){
	        return this.example;
	    }
	
}
