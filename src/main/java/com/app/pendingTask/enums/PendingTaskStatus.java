package com.app.pendingTask.enums;

public enum PendingTaskStatus {
  PENDING("PENDING"),
  COMPLETED("COMPLETED"),
  IN_PROCESS("IN_PROCESS");

  private String pendingTaskStatus;

  PendingTaskStatus(String pendingTaskStatus) {
    this.pendingTaskStatus = pendingTaskStatus;
  }

  public String toString() {
    return this.pendingTaskStatus;
  }
}
