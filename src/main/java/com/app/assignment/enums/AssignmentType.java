package com.app.assignment.enums;

public enum AssignmentType {
  QUIZ_ASSIGNMENT("QUIZ_ASSIGNMENT"),
  ASSIGNMENT("ASSIGNMENT"),
  QUESTION("QUESTION"),
  MATERIAL("MATERIAL");

  private String assignmentType;

  AssignmentType(String assignmentType) {
    this.assignmentType = assignmentType;
  }

  public String toString() {
    return this.assignmentType;
  }
}
