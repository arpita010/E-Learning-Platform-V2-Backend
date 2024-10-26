package com.app.policy.enums;

public enum ResourceName {
  AUTH_INIT_API("AUTH_INIT_API");
  private String resourceName;

  ResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  public String toString() {
    return this.resourceName;
  }
}
