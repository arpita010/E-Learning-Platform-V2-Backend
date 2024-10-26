package com.app.feedback.response;

import com.app.commons.SuperResponse;
import com.app.feedback.Feedback;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse extends SuperResponse {
  private Long feedbackId;
  private String content;
  private String postedBy;
  private Long courseId;
  private Date postedAt;

  public FeedbackResponse(Feedback feedback) {
    this.feedbackId = feedback.getId();
    this.content = feedback.getContent();
    this.postedBy = feedback.getPostedBy();
    this.courseId = feedback.getCourseId();
    this.postedAt = feedback.getCreatedAt();
  }
}
