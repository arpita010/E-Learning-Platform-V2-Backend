package com.app.enums;

public enum FileType {
  CHAPTER_IMAGE("CHAPTER_IMAGE"),
  CHAPTER_VIDEO("CHAPTER_VIDEO"),
  CLASS_IMAGE("CLASS_IMAGE"),
  COURSE_IMAGE("COURSE_IMAGE");
  private String fileType;

  FileType(String fileType) {
    this.fileType = fileType;
  }

  public String toString() {
    return this.fileType;
  }
}
