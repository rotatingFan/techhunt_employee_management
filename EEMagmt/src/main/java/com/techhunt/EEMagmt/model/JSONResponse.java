package com.techhunt.EEMagmt.model;

public class JSONResponse {
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	
	private String status;
	private String errorMessage;
	private Object data;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
