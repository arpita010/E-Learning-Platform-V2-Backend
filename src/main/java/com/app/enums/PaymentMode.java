package com.app.enums;

public enum PaymentMode {
  UPI("UPI"),
  NET_BANKING("NET_BANKING");

  private String paymentMode;

  PaymentMode(String paymentMode) {
    this.paymentMode = paymentMode;
  }

  public String toString() {
    return this.paymentMode;
  }
}
