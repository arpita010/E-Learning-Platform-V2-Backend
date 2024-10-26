package com.app.question.response;

import com.app.answerOption.response.AnswerOptionResponse;
import com.app.question.AssignmentQuestion;
import com.app.question.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentQuestionResponse {
  private Long questionId;
  private String question;
  private QuestionType type;
  private Boolean isRequired;
  private Long assignmentId;
  private List<AnswerOptionResponse> options;

  public AssignmentQuestionResponse(AssignmentQuestion question) {
    this.questionId = question.getId();
    this.question = question.getQuestion();
    this.type = question.getType();
    this.isRequired = question.getIsRequired();
    this.assignmentId = question.getAssignmentId();
  }
}
