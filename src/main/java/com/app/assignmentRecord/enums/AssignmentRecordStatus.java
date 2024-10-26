package com.app.assignmentRecord.enums;

public enum AssignmentRecordStatus {
  ASSIGNED("ASSIGNED"),
  SUBMITTED("SUBMITTED"),
  GRADED("GRADED"),
  DONE("DONE");
  private String assignmentRecordStatus;

  AssignmentRecordStatus(String assignmentRecordStatus) {
    this.assignmentRecordStatus = assignmentRecordStatus;
  }

  public String toString() {
    return this.assignmentRecordStatus;
  }
}
