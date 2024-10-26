package com.app.announcement.enums;

public enum AnnouncementType {
  POST("POST"),
  ASSIGNMENT("ASSIGNMENT");

  private String announcementType;

  AnnouncementType(String announcementType) {
    this.announcementType = announcementType;
  }

  public String toString() {
    return this.announcementType;
  }
}
