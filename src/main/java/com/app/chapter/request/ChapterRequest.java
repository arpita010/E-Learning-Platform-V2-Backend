package com.app.chapter.request;

import com.app.chapter.Chapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterRequest {
  private String name;
  private Long durationInMins;
  private String description;

  public Chapter toChapter() {
    return Chapter.builder()
        .name(this.name)
        .description(this.description)
        .durationInMins(this.durationInMins)
        .build();
  }

  public void updateDetails(Chapter chapter) {
    Optional.ofNullable(this.name).ifPresent(chapter::setName);
    Optional.ofNullable(this.durationInMins).ifPresent(chapter::setDurationInMins);
    Optional.ofNullable(this.description).ifPresent(chapter::setDescription);
  }
}
