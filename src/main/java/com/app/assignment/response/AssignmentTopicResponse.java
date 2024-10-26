package com.app.assignment.response;

import com.app.topic.response.TopicResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentTopicResponse {
  private TopicResponse topic;
  private List<AssignmentResponse> assignmentsList;
}
