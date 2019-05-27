package com.inova.etf.otp.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="otp")
public class Otp {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "user_identifier", unique = true)
	private String userIdentifier;
	@Column(name = "gen_otp")
	private String genOtp;
	@Column(name = "created_date")
	@CreationTimestamp
	private Timestamp createdDate;
	@Column(name = "updated_date")
	@UpdateTimestamp
	private Timestamp updatedDate;
	@Column(name = "otp_attempt_count")
	private int otpAttemptCount;
	@Column(name = "is_verified")
	private boolean isVerified;
	@Column(name = "otp_request_count")
	private int otpRequestCount;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserIdentifier() {
		return userIdentifier;
	}
	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}
	public String getGenOtp() {
		return genOtp;
	}
	public void setGenOtp(String genOtp) {
		this.genOtp = genOtp;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public int getOtpAttemptCount() {
		return otpAttemptCount;
	}
	public void setOtpAttemptCount(int otpAttemptCount) {
		this.otpAttemptCount = otpAttemptCount;
	}
	public boolean isVerified() {
		return isVerified;
	}
	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
	public int getOtpRequestCount() {
		return otpRequestCount;
	}
	public void setOtpRequestCount(int otpRequestCount) {
		this.otpRequestCount = otpRequestCount;
	}
}
