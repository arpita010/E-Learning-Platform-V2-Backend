package com.app.policy.enums;

public enum ResourceType {
  API("API");
  private String resourceType;

  ResourceType(String resourceType) {
    this.resourceType = resourceType;
  }

  public String toString() {
    return this.resourceType;
  }
}
