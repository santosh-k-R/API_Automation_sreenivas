package com.cisco.services.api_automation.pojo.response.architecturereview;

import java.util.List;

public class CBPRuleCountPojo 
	{
	    private int TotalCounts;

	    private List<BPRulesDetailsPojo> BPRulesDetails;

	    private String info;

	    public void setTotalCounts(int TotalCounts){
	        this.TotalCounts = TotalCounts;
	    }
	    public int getTotalCounts(){
	        return this.TotalCounts;
	    }
	    public void setBPRulesDetails(List<BPRulesDetailsPojo> BPRulesDetails){
	        this.BPRulesDetails = BPRulesDetails;
	    }
	    public List<BPRulesDetailsPojo> getBPRulesDetails(){
	        return this.BPRulesDetails;
	    }
	    public void setInfo(String info){
	        this.info = info;
	    }
	    public String getInfo(){
	        return this.info;
	    }
	}