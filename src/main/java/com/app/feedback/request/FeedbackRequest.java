package com.app.feedback.request;

import com.app.feedback.Feedback;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequest {
  private Long courseId;
  private String content;

  public Feedback toFeedback() {
    return Feedback.builder().courseId(this.courseId).content(this.content).build();
  }
}
