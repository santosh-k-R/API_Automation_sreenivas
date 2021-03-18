package com.cisco.services.api_automation.pojo.response.architecturereview;

import com.cisco.services.api_automation.pojo.response.architecturereview.AssuranceCompliance.*;

public class DeviceCompliancePojo 
{
	
	    private AssuranceCompliance assuranceCompliance;

	    private OverallCompliance overallCompliance;

	    private SwimCompliance swimCompliance;

	    private PnpCompliance pnpCompliance;

	    private DnacCompliance dnacCompliance;

	    private SdaCompliance sdaCompliance;

	    public void setAssuranceCompliance(AssuranceCompliance assuranceCompliance){
	        this.assuranceCompliance = assuranceCompliance;
	    }
	    public AssuranceCompliance getAssuranceCompliance(){
	        return this.assuranceCompliance;
	    }
	    public void setOverallCompliance(OverallCompliance overallCompliance){
	        this.overallCompliance = overallCompliance;
	    }
	    public OverallCompliance getOverallCompliance(){
	        return this.overallCompliance;
	    }
	    public void setSwimCompliance(SwimCompliance swimCompliance){
	        this.swimCompliance = swimCompliance;
	    }
	    public SwimCompliance getSwimCompliance(){
	        return this.swimCompliance;
	    }
	    public void setPnpCompliance(PnpCompliance pnpCompliance){
	        this.pnpCompliance = pnpCompliance;
	    }
	    public PnpCompliance getPnpCompliance(){
	        return this.pnpCompliance;
	    }
	    public void setDnacCompliance(DnacCompliance dnacCompliance){
	        this.dnacCompliance = dnacCompliance;
	    }
	    public DnacCompliance getDnacCompliance(){
	        return this.dnacCompliance;
	    }
	    public void setSdaCompliance(SdaCompliance sdaCompliance){
	        this.sdaCompliance = sdaCompliance;
	    }
	    public SdaCompliance getSdaCompliance(){
	        return this.sdaCompliance;
	    }
}
