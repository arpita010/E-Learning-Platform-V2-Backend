package com.app.announcement;

import com.app.announcement.enums.AnnouncementStatus;
import com.app.announcement.enums.AnnouncementType;
import com.app.commons.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Announcement extends SuperEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "longtext")
  private String announcement;

  private Long classroomId;

  private Long creatorId; // this will the id of user who created this post.

  private AnnouncementType type;

  private AnnouncementStatus status;
}
