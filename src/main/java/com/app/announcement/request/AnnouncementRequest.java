package com.app.announcement.request;

import com.app.announcement.Announcement;
import com.app.announcement.enums.AnnouncementStatus;
import com.app.announcement.enums.AnnouncementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementRequest {
  private String announcement;
  private Long classroomId;
  private AnnouncementType type;
  private AnnouncementStatus status;
  private List<String> peoples;

  public Announcement toAnnouncement() {
    return Announcement.builder()
        .announcement(this.announcement)
        .classroomId(this.classroomId)
        .type(this.type)
        .status(this.status)
        .build();
  }
}
