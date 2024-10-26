package com.app.answerOption;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerOptionRepo extends CrudRepository<AnswerOption, Long> {
  Optional<List<AnswerOption>> findAllByQuestionId(Long questionId);
}
