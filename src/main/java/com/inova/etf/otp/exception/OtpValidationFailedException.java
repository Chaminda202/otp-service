package com.inova.etf.otp.exception;

import com.inova.etf.otp.util.Util;

public class OtpValidationFailedException extends Exception {
	private static final long serialVersionUID = 5447513430339596645L;
	@Override
	public String getMessage() {
		return Util.OTP_VALIDATION_FAILED_MESSAGE;
	}
}
