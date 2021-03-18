package com.cisco.services.api_automation.pojo.response.assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpectedActualSortResultPojo {
	
	private List<String> expectedSortedList = new ArrayList<String>();
	private List<String> actualSortedList = new ArrayList<String>();
	private List<Integer> expectedSortedListInteger = new ArrayList<Integer>();
	private List<Integer> actualSortedListInteger = new ArrayList<Integer>();
	
	public List<String> getExpectedSortedList() {
		return expectedSortedList;
	}
	public void setExpectedSortedList(List<String> expectedSortedList) {
		this.expectedSortedList = expectedSortedList;
	}
	public List<String> getActualSortedList() {
		return actualSortedList;
	}
	public void setActualSortedList(List<String> actualSortedList) {
		this.actualSortedList = actualSortedList;
	}
	public List<Integer> getExpectedSortedListInteger() {
		return expectedSortedListInteger;
	}
	public void setExpectedSortedListInteger(List<Integer> expectedSortedListInteger) {
		this.expectedSortedListInteger = expectedSortedListInteger;
	}
	public List<Integer> getActualSortedListInteger() {
		return actualSortedListInteger;
	}
	public void setActualSortedListInteger(List<Integer> actualSortedListInteger) {
		this.actualSortedListInteger = actualSortedListInteger;
	}
	
	
}
