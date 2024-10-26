package com.app.question.request;

import com.app.answerOption.request.AnswerOptionRequest;
import com.app.question.AssignmentQuestion;
import com.app.question.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentQuestionRequest {
  private String question;
  private Boolean isRequired;
  private QuestionType type;
  private List<AnswerOptionRequest> options;

  public AssignmentQuestion toAssignmentQuestion() {
    return AssignmentQuestion.builder()
        .question(this.question)
        .isRequired(this.isRequired)
        .type(this.type)
        .build();
  }
}
