package com.app.chapter.response;

import com.app.chapter.Chapter;
import com.app.commons.SuperResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChapterResponse extends SuperResponse {
  private Long chapterId;
  private String name;
  private Long durationInMins;
  private String description;
  private String thumbnailUrl;
  private String videoUrl;
  private Long courseId;

  public ChapterResponse(Chapter chapter) {
    this.chapterId = chapter.getId();
    this.name = chapter.getName();
    this.durationInMins = chapter.getDurationInMins();
    this.description = chapter.getDescription();
    this.thumbnailUrl = chapter.getThumbnailUrl();
    this.videoUrl = chapter.getVideoUrl();
    this.courseId = chapter.getCourse().getId();
  }
}
