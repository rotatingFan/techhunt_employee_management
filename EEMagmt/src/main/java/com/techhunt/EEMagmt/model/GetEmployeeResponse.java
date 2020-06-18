package com.techhunt.EEMagmt.model;

import java.util.List;

public class GetEmployeeResponse {
	private List<Employee> results;
	private int maxPage;
	private String error;
	public List<Employee> getResults() {
		return results;
	}

	public void setResults(List<Employee> results) {
		this.results = results;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
