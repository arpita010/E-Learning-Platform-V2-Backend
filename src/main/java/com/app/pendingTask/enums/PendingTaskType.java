package com.app.pendingTask.enums;

public enum PendingTaskType {
  ASSIGNMENT("ASSIGNMENT");
  private String pendingTaskType;

  PendingTaskType(String pendingTaskType) {
    this.pendingTaskType = pendingTaskType;
  }

  public String toString() {
    return this.pendingTaskType;
  }
}
