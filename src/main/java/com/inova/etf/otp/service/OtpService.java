package com.inova.etf.otp.service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.inova.etf.otp.entity.Otp;
import com.inova.etf.otp.exception.OtpAlreadyUsedException;
import com.inova.etf.otp.exception.OtpAttemptExceedException;
import com.inova.etf.otp.exception.OtpExpiredException;
import com.inova.etf.otp.exception.OtpInvalidException;
import com.inova.etf.otp.exception.OtpNotExistException;
import com.inova.etf.otp.repository.OtpRepository;
import com.inova.etf.otp.util.Util;

@Service
public class OtpService {
	private Logger logger;
	private OtpRepository otpRepository;

	@Value("${otp.expiration}")
	private int expiration;

	@Value("${otp.max.attempts}")
	private int otpMaxAttempts;

	@Autowired
	public OtpService(OtpRepository otpRepository) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.otpRepository = otpRepository;
	}

	public String generateOtp(String userIdentifier) {
		logger.info("Generate otp service {}", userIdentifier);
		Otp otp = this.otpRepository.findByUserIdentifier(userIdentifier);
		String generatedOtp = genOtp();
		if (otp != null) {
			otp.setGenOtp(generatedOtp);
			otp.setOtpAttemptCount(0);
			otp.setVerified(false);
			otp.setCreatedDate(new Timestamp(new Date().getTime()));
			otp.setOtpRequestCount(otp.getOtpRequestCount() + 1);
		} else {
			otp = new Otp();
			otp.setGenOtp(generatedOtp);
			otp.setUserIdentifier(userIdentifier);
			otp.setOtpRequestCount(1);
		}
		this.otpRepository.save(otp);
		logger.info("Generate otp service otp -> {} -> {}", userIdentifier, Util.STATUS_SUCCESS);
		return generatedOtp;
	}

	public boolean valiateOtp(String userIdentifier, String otpVal) throws OtpNotExistException, OtpExpiredException,
			OtpInvalidException, OtpAttemptExceedException, OtpAlreadyUsedException {
		logger.info("Validate otp service {} -> {}", userIdentifier, otpVal);
		Otp otp = this.otpRepository.findByUserIdentifier(userIdentifier);
		if (otp == null) {
			throw new OtpNotExistException();
		} else {
			long now = (new Date()).getTime();
			long validity = otp.getCreatedDate().getTime() + expiration * 1000;
			otp.setOtpAttemptCount(otp.getOtpAttemptCount() + 1);

			if (otp.isVerified()) {
				throw new OtpAlreadyUsedException();
			}
			if (otp.getOtpAttemptCount() > otpMaxAttempts) {
				throw new OtpAttemptExceedException();
			} else {
				// otp validation period
				if (validity < now) {
					throw new OtpExpiredException();
				} else {
					if (!otp.getGenOtp().equals(otpVal)) {
						this.otpRepository.save(otp);
						throw new OtpInvalidException();
					}
					logger.info("Validate otp service {}", Util.STATUS_SUCCESS);
					otp.setVerified(true);
					this.otpRepository.save(otp);
					return true;
				}
			}
		}
	}

	private String genOtp() {
		String characters = "0123456789";
		SecureRandom random = new SecureRandom();
		char[] text = new char[4];
		for (int i = 0; i < 4; i++) {
			text[i] = characters.charAt(random.nextInt(characters.length()));
		}
		return new String(text);
	}

}
