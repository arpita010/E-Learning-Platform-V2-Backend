package com.app.answerOption.request;

import com.app.answerOption.AnswerOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerOptionRequest {
  private String value;
  private Boolean isCorrect;

  public AnswerOption toAnswerOption() {
    return AnswerOption.builder().value(this.value).isCorrect(this.isCorrect).build();
  }
}
