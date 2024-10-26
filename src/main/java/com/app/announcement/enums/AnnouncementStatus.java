package com.app.announcement.enums;

public enum AnnouncementStatus {
  POSTED("POSTED"),
  SCHEDULED("SCHEDULED"),
  DRAFT("DRAFT");
  private String announcementStatus;

  AnnouncementStatus(String announcementStatus) {
    this.announcementStatus = announcementStatus;
  }

  public String toString() {
    return this.announcementStatus;
  }
}
