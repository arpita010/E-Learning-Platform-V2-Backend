package com.app.topic.response;

import com.app.commons.SuperResponse;
import com.app.topic.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicResponse extends SuperResponse {
  private Long topicId;
  private String name;

  public TopicResponse(Topic topic) {
    this.topicId = topic.getId();
    this.name = topic.getName();
  }
}
