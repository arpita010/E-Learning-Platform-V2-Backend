package com.app.answerOption;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnswerOptionService {
  private final AnswerOptionRepo answerOptionRepo;

  public AnswerOption save(AnswerOption option) {
    return answerOptionRepo.save(option);
  }

  public List<AnswerOption> findAllByQuestion(Long questionId) throws Exception {
    Optional<List<AnswerOption>> opt = answerOptionRepo.findAllByQuestionId(questionId);
    if (opt.isEmpty()) throw new Exception("No options found");
    return opt.get();
  }
}
