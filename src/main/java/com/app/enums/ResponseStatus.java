package com.app.enums;

public enum ResponseStatus {
  SUCCESS("SUCCESS"),
  FAILED("FAILED");

  private String responseStatus;

  ResponseStatus(String responseStatus) {
    this.responseStatus = responseStatus;
  }

  public String toString() {
    return this.responseStatus;
  }
}
