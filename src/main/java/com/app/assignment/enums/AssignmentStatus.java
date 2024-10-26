package com.app.assignment.enums;

public enum AssignmentStatus {
  POSTED("POSTED"),
  SCHEDULED("SCHEDULED"),
  DRAFT("DRAFT");

  private String assignmentStatus;

  AssignmentStatus(String assignmentStatus) {
    this.assignmentStatus = assignmentStatus;
  }

  public String toString() {
    return this.assignmentStatus;
  }
}
