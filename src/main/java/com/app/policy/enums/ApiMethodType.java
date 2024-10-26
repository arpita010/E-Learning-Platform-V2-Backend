package com.app.policy.enums;

public enum ApiMethodType {
  GET("GET"),
  DELETE("DELETE"),
  POST("POST"),
  PUT("PUT");
  private String apiMethodType;

  ApiMethodType(String apiMethodType) {
    this.apiMethodType = apiMethodType;
  }

  public String toString() {
    return this.apiMethodType;
  }
}
