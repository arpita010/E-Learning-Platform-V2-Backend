package com.app.enums;

public enum PaymentStatus {
  SUCCESS("SUCCESS"),
  PENDING("PENDING"),
  FAILED("FAILED");

  private String paymentStatus;

  PaymentStatus(String paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  public String toString() {
    return this.paymentStatus;
  }
}
