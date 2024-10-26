package com.app.enums;

public enum OtpType {
  SIGNIN_OTP("SIGNIN_OTP"),
  SIGNUP_OTP("SIGNUP_OTP"),
  PAYMENT_OTP("PAYMENT_OTP");

  private String otpType;

  OtpType(String otpType) {
    this.otpType = otpType;
  }

  public String toString() {
    return this.otpType;
  }
}
