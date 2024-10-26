package com.app.enums;

public enum UserRole {
  INSTRUCTOR("INSTRUCTOR"),
  STUDENT("STUDENT");

  private String userRole;

  UserRole(String userRole) {
    this.userRole = userRole;
  }

  public String toString() {
    return this.userRole;
  }
}
