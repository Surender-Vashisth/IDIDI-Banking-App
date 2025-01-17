package com.ididibank.customer_management_service.dto;

import org.springframework.http.HttpStatus;

public class ApiResponseDto {

	private HttpStatus status;
	private String message;
	private Object data;
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

}
