package com.app.assignment.response;

import com.app.assignment.Assignment;
import com.app.assignment.enums.AssignmentStatus;
import com.app.assignment.enums.AssignmentType;
import com.app.question.response.AssignmentQuestionResponse;
import com.app.topic.response.TopicResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentResponse {
  private Long assignmentId;
  private String title;
  private String instructions;
  private Long classroomId;
  private Date dueDate;
  private Double points;
  private AssignmentStatus assignmentStatus;
  private AssignmentType type;
  private TopicResponse topic;
  private List<AssignmentQuestionResponse> questions;

  public AssignmentResponse(Assignment assignment) {
    this.assignmentId = assignment.getId();
    this.title = assignment.getTitle();
    this.instructions = assignment.getInstructions();
    this.classroomId = assignment.getClassroomId();
    this.dueDate = assignment.getDueDate();
    this.points = assignment.getPoints();
    this.assignmentStatus = assignment.getStatus();
    this.type = assignment.getType();
    this.topic = null != assignment.getTopic() ? new TopicResponse(assignment.getTopic()) : null;
  }
}

// assignment - type - question, assignment, quiz assignment, material
