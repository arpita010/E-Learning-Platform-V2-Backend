package com.app.assignment.request;

import com.app.assignment.Assignment;
import com.app.assignment.enums.AssignmentStatus;
import com.app.assignment.enums.AssignmentType;
import com.app.question.request.AssignmentQuestionRequest;
import com.app.topic.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentRequest {
  private String title;
  private String instructions;
  private Long topicId; // can be null
  private Long classroomId;
  private Date dueDate;
  private Double points;
  private List<String> assignees; // list of emails of students
  private AssignmentType type;
  private AssignmentStatus status;
  private List<AssignmentQuestionRequest> questions;

  public Assignment toAssignment(Topic topic) {
    return Assignment.builder()
        .title(this.title)
        .instructions(this.instructions)
        .topic(topic)
        .classroomId(this.classroomId)
        .dueDate(this.dueDate)
        .points(this.points)
        .type(this.type)
        .status(this.status)
        .build();
  }
}
