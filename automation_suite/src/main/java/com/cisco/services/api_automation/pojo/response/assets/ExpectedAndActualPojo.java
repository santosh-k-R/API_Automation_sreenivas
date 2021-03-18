package com.cisco.services.api_automation.pojo.response.assets;

import java.util.ArrayList;
import java.util.List;

import io.restassured.response.Response;

public class ExpectedAndActualPojo {
	
	private Response expectedResponse;
	private Response actualResponse;
	private int expectedRecordCount;
	private int ActualRecordCount;
	private int expectedPaginationTotal;
	private int expectedPaginationPage;
	private int expectedPaginationPages;
	private int expectedPaginationRows;
	private int actualPaginationTotal;
	private int actualPaginationPage;
	private int actualPaginationPages;
	private int actualPaginationRows;
	private List<String> expectedSortedList = new ArrayList<String>();
	private List<String> actualSortedList = new ArrayList<String>();
	private List<Integer> expectedSortedListInteger = new ArrayList<Integer>();
	private List<Integer> actualSortedListInteger = new ArrayList<Integer>();
	
	public int getExpectedPaginationPage() {
		return expectedPaginationPage;
	}
	public void setExpectedPaginationPage(int expectedPaginationPage) {
		this.expectedPaginationPage = expectedPaginationPage;
	}
	public int getExpectedPaginationPages() {
		return expectedPaginationPages;
	}
	public void setExpectedPaginationPages(int expectedPaginationPages) {
		this.expectedPaginationPages = expectedPaginationPages;
	}
	public int getExpectedPaginationRows() {
		return expectedPaginationRows;
	}
	public void setExpectedPaginationRows(int expectedPaginationRows) {
		this.expectedPaginationRows = expectedPaginationRows;
	}
	public int getActualPaginationPage() {
		return actualPaginationPage;
	}
	public void setActualPaginationPage(int actualPaginationPage) {
		this.actualPaginationPage = actualPaginationPage;
	}
	public int getActualPaginationPages() {
		return actualPaginationPages;
	}
	public void setActualPaginationPages(int actualPaginationPages) {
		this.actualPaginationPages = actualPaginationPages;
	}
	public int getActualPaginationRows() {
		return actualPaginationRows;
	}
	public void setActualPaginationRows(int actualPaginationRows) {
		this.actualPaginationRows = actualPaginationRows;
	}
	
	public Response getExpectedResponse() {
		return expectedResponse;
	}
	public void setExpectedResponse(Response expectedResponse) {
		this.expectedResponse = expectedResponse;
	}
	public Response getActualResponse() {
		return actualResponse;
	}
	public void setActualResponse(Response actualResponse) {
		this.actualResponse = actualResponse;
	}
	public int getExpectedRecordCount() {
		return expectedRecordCount;
	}
	public void setExpectedRecordCount(int expectedRecordCount) {
		this.expectedRecordCount = expectedRecordCount;
	}
	public int getActualRecordCount() {
		return ActualRecordCount;
	}
	public void setActualRecordCount(int actualRecordCount) {
		ActualRecordCount = actualRecordCount;
	}

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
	public int getExpectedPaginationTotal() {
		return expectedPaginationTotal;
	}
	public void setExpectedPaginationTotal(int expectedPaginationTotal) {
		this.expectedPaginationTotal = expectedPaginationTotal;
	}
	public int getActualPaginationTotal() {
		return actualPaginationTotal;
	}
	public void setActualPaginationTotal(int actualPaginationTotal) {
		this.actualPaginationTotal = actualPaginationTotal;
	}
	
	

}
