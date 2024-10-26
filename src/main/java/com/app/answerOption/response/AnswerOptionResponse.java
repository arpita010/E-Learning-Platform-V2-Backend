package com.app.answerOption.response;

import com.app.answerOption.AnswerOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerOptionResponse {
  private Long answerId;
  private String value;
  private Boolean isCorrect;
  private Long questionId;

  public AnswerOptionResponse(AnswerOption option) {
    this.answerId = option.getId();
    this.value = option.getValue();
    this.isCorrect = option.getIsCorrect();
    this.questionId = option.getQuestionId();
  }
}
