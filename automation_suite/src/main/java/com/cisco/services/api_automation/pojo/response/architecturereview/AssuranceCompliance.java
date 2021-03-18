package com.cisco.services.api_automation.pojo.response.architecturereview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssuranceCompliance
{
    @SerializedName("yes")
    @Expose
	private int yes;

    @SerializedName("no")
    @Expose
    private int no;
    
    @SerializedName("not evaluated")
    @Expose
    private int not_evaluated;

    public void setYes(int yes){
        this.yes = yes;
    }
    public int getYes(){
        return this.yes;
    }
    public void setNo(int no){
        this.no = no;
    }
    public int getNo(){
        return this.no;
    }
    public void setNot_evaluated(int not_evaluated){
        this.not_evaluated = not_evaluated;
        
    }
    public int getNot_evaluated(){
        return this.not_evaluated;
    }
    
    public class OverallCompliance
    {
    	@SerializedName("yes")
        @Expose
    	private int yes;
    	
    	@SerializedName("no")
        @Expose
        private int no;

    	@SerializedName("not evaluated")
    	@Expose
        private int not_evaluated;

    	public void setYes(int yes){
            this.yes = yes;
        }
        public int getYes(){
            return this.yes;
        }
        public void setNo(int no){
            this.no = no;
        }
        public int getNo(){
            return this.no;
        }
        public void setNot_evaluated(int not_evaluated){
            this.not_evaluated = not_evaluated;
        }
        public int getNot_evaluated(){
            return this.not_evaluated;
        }
    }
    
    public class PnpCompliance
    {
    	@SerializedName("yes")
        @Expose
    	private int yes;
    	
    	@SerializedName("no")
        @Expose
        private int no;

    	@SerializedName("not evaluated")
    	@Expose
        private int not_evaluated;

    	public void setYes(int yes){
            this.yes = yes;
        }
        public int getYes(){
            return this.yes;
        }
        public void setNo(int no){
            this.no = no;
        }
        public int getNo(){
            return this.no;
        }
        public void setNot_evaluated(int not_evaluated){
            this.not_evaluated = not_evaluated;
        }
        public int getNot_evaluated(){
            return this.not_evaluated;
        }
    }
    
    public class SwimCompliance
    {
    	@SerializedName("yes")
        @Expose
    	private int yes;
    	
    	@SerializedName("no")
        @Expose
        private int no;

    	@SerializedName("not evaluated")
    	@Expose
        private int not_evaluated;

    	public void setYes(int yes){
            this.yes = yes;
        }
        public int getYes(){
            return this.yes;
        }
        public void setNo(int no){
            this.no = no;
        }
        public int getNo(){
            return this.no;
        }
        public void setNot_evaluated(int not_evaluated){
            this.not_evaluated = not_evaluated;
        }
        public int getNot_evaluated(){
            return this.not_evaluated;
        }
    }
    
    public class DnacCompliance
    {
    	@SerializedName("yes")
        @Expose
    	private int yes;
    	
    	@SerializedName("no")
        @Expose
        private int no;

    	@SerializedName("not evaluated")
    	@Expose
        private int not_evaluated;

    	public void setYes(int yes){
            this.yes = yes;
        }
        public int getYes(){
            return this.yes;
        }
        public void setNo(int no){
            this.no = no;
        }
        public int getNo(){
            return this.no;
        }
        public void setNot_evaluated(int not_evaluated){
            this.not_evaluated = not_evaluated;
        }
        public int getNot_evaluated(){
            return this.not_evaluated;
        }
    }
    
    public class SdaCompliance
    {
    	@SerializedName("yes")
        @Expose
    	private int yes;
    	
    	@SerializedName("no")
        @Expose
        private int no;

    	@SerializedName("not evaluated")
    	@Expose
        private int not_evaluated;

    	public void setYes(int yes){
            this.yes = yes;
        }
        public int getYes(){
            return this.yes;
        }
        public void setNo(int no){
            this.no = no;
        }
        public int getNo(){
            return this.no;
        }
        public void setNot_evaluated(int not_evaluated){
            this.not_evaluated = not_evaluated;
        }
        public int getNot_evaluated(){
            return this.not_evaluated;
        }
    }
}
