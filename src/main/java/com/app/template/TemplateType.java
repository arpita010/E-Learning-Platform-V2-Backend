package com.app.template;

public enum TemplateType {
  CLASSROOM_JOIN_INIT_EMAIL("CLASSROOM_JOIN_INIT_EMAIL", "", "", ""),
  SIGNUP_INIT_EMAIL("SIGNUP_INIT_EMAIL", "", "", ""),
  SIGNIN_INIT_EMAIL("SIGNIN_INIT_EMAIL", "", "", "");

  private String templateType;
  private String folder;
  private String fileName;
  private String subject;

  TemplateType(String templateType, String folder, String fileName, String subject) {
    this.templateType = templateType;
    this.folder = folder;
    this.fileName = fileName;
    this.subject = subject;
  }

  public String toString() {
    return this.templateType;
  }

  public String folder() {
    return this.folder;
  }

  public String fileName() {
    return this.fileName;
  }

  public String subject() {
    return this.subject;
  }
}
