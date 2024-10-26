package com.app.topic.request;

import com.app.topic.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicRequest {
  private String name;
  private Long classroomId;

  public Topic toTopic() {
    Topic topic = new Topic();
    topic.setName(this.name);
    topic.setClassroomId(classroomId);
    return topic;
  }
}
