package com.inova.etf.otp.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtpValidateRequest {
	@JsonProperty("user_identifier")
	String userIdentifier;
	@JsonProperty("otp")
	String otp;
	public String getUserIdentifier() {
		return userIdentifier;
	}
	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
}
