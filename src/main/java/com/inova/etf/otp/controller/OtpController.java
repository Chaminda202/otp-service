package com.inova.etf.otp.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inova.etf.otp.exception.OtpValidationFailedException;
import com.inova.etf.otp.request.OtpGenerateRequest;
import com.inova.etf.otp.request.OtpValidateRequest;
import com.inova.etf.otp.service.OtpService;
import com.inova.etf.otp.util.Util;

@RestController
@RequestMapping("/api/otp")
public class OtpController {
	private Logger logger;
	private OtpService otpService;

	public OtpController(OtpService otpService) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.otpService = otpService;
	}

	@PostMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> generateOTP(@RequestBody OtpGenerateRequest request) {
		logger.info("Generate otp controller {}", request.getUserIdentifier());
		Map<String, String> response = new HashMap<>();
		try {
			response.put("OTP", this.otpService.generateOtp(request.getUserIdentifier()));
			response.put("STATUS", Util.STATUS_SUCCESS);
			response.put("MESSAGE", Util.OTP_GENERATE_SUCCESS_MESSAGE);
			logger.info("Validate otp controller {}", Util.STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error("Generate otp controller {} -> {}", Util.STATUS_FAILED, e.getMessage());
			response.put("STATUS", Util.STATUS_FAILED);
			response.put("MESSAGE", Util.OTP_GENERATION_FAILED_MESSAGE);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> validateOTP(@RequestBody OtpValidateRequest request) {
		logger.info("Validate otp controller {} -> {}", request.getUserIdentifier(), request.getOtp());
		Map<String, String> response = new HashMap<>();
		try {
			this.otpService.valiateOtp(request.getUserIdentifier(), request.getOtp());
			response.put("STATUS", Util.STATUS_SUCCESS);
			response.put("MESSAGE", Util.OTP_VALIDATE_SUCCESS_MESSAGE);
			logger.info("Validate otp controller {}", Util.STATUS_SUCCESS);
		} catch (OtpValidationFailedException ex) {
			logger.error("Validate otp controller {} -> {}", Util.STATUS_FAILED, ex.getMessage());
			response.put("STATUS", Util.STATUS_FAILED);
			response.put("MESSAGE", ex.getMessage());
		}  catch (Exception e) {
			logger.error("Validate otp controller {} -> {}", Util.STATUS_FAILED, e.getMessage());
			response.put("STATUS", Util.STATUS_FAILED);
			response.put("MESSAGE", Util.OTP_VALIDATION_FAILED_MESSAGE);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/hello")
	public String helloWorld(){
		return "Say, Hello world...!!!";
	}
}
