package com.inova.etf.otp.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtpGenerateRequest {
	@JsonProperty("user_identifier")
	String userIdentifier;

	public String getUserIdentifier() {
		return userIdentifier;
	}
	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}
}
