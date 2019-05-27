package com.inova.etf.otp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inova.etf.otp.entity.Otp;

public interface OtpRepository extends JpaRepository<Otp, Integer> {
	Otp findByUserIdentifier(String userIdentifier);
}
