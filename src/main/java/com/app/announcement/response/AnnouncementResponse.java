package com.app.announcement.response;

import com.app.announcement.Announcement;
import com.app.announcement.enums.AnnouncementStatus;
import com.app.announcement.enums.AnnouncementType;
import com.app.comments.response.CommentResponse;
import com.app.commons.SuperResponse;
import com.app.user.User;
import com.app.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementResponse extends SuperResponse {
  private Long announcementId;
  private String announcement;
  private Long classroomId;
  private UserResponse creator;
  private AnnouncementType type;
  private AnnouncementStatus announcementStatus;

  public AnnouncementResponse(Announcement announcement, User creator) {
    this.announcementId = announcement.getId();
    this.announcement = announcement.getAnnouncement();
    this.classroomId = announcement.getClassroomId();
    this.type = announcement.getType();
    this.creator = new UserResponse(creator);
    this.announcementStatus = announcement.getStatus();
  }
}
