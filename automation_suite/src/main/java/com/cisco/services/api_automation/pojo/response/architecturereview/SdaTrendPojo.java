package com.cisco.services.api_automation.pojo.response.architecturereview;

public class SdaTrendPojo {
	
	    public static Object details;

		private int noOfDevices;

	    private int noOfEndpoints;

	    private int noOfFabrics;

	    private int noOfWlc;

	    private int noOfAccessPoints;

	    private String endpointsPeakTime;

	    private String devicesPeakTime;

	    private String fabricsPeakTime;

	    private String wlcPeakTime;

	    private String accessPointsPeakTime;

	    public void setNoOfDevices(int noOfDevices){
	        this.noOfDevices = noOfDevices;
	    }
	    public int getNoOfDevices(){
	        return this.noOfDevices;
	    }
	    public void setNoOfEndpoints(int noOfEndpoints){
	        this.noOfEndpoints = noOfEndpoints;
	    }
	    public int getNoOfEndpoints(){
	        return this.noOfEndpoints;
	    }
	    public void setNoOfFabrics(int noOfFabrics){
	        this.noOfFabrics = noOfFabrics;
	    }
	    public int getNoOfFabrics(){
	        return this.noOfFabrics;
	    }
	    public void setNoOfWlc(int noOfWlc){
	        this.noOfWlc = noOfWlc;
	    }
	    public int getNoOfWlc(){
	        return this.noOfWlc;
	    }
	    public void setNoOfAccessPoints(int noOfAccessPoints){
	        this.noOfAccessPoints = noOfAccessPoints;
	    }
	    public int getNoOfAccessPoints(){
	        return this.noOfAccessPoints;
	    }
	    public void setEndpointsPeakTime(String endpointsPeakTime){
	        this.endpointsPeakTime = endpointsPeakTime;
	    }
	    public String getEndpointsPeakTime(){
	        return this.endpointsPeakTime;
	    }
	    public void setDevicesPeakTime(String devicesPeakTime){
	        this.devicesPeakTime = devicesPeakTime;
	    }
	    public String getDevicesPeakTime(){
	        return this.devicesPeakTime;
	    }
	    public void setFabricsPeakTime(String fabricsPeakTime){
	        this.fabricsPeakTime = fabricsPeakTime;
	    }
	    public String getFabricsPeakTime(){
	        return this.fabricsPeakTime;
	    }
	    public void setWlcPeakTime(String wlcPeakTime){
	        this.wlcPeakTime = wlcPeakTime;
	    }
	    public String getWlcPeakTime(){
	        return this.wlcPeakTime;
	    }
	    public void setAccessPointsPeakTime(String accessPointsPeakTime){
	        this.accessPointsPeakTime = accessPointsPeakTime;
	    }
	    public String getAccessPointsPeakTime(){
	        return this.accessPointsPeakTime;
	    }
		public int size() {
			// TODO Auto-generated method stub
			return 0;
		}
	
}
