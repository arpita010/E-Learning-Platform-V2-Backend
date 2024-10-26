package com.app.question.enums;

public enum QuestionType {
  SHORT_ANSWER("SHORT_ANSWER"),
  MULTIPLE_CHOICE("MULTIPLE_CHOICE");

  private String questionType;

  QuestionType(String questionType) {
    this.questionType = questionType;
  }

  public String toString() {
    return this.questionType;
  }
}
