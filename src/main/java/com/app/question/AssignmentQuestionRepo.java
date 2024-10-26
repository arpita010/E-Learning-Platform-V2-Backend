package com.app.question;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentQuestionRepo extends CrudRepository<AssignmentQuestion, Long> {
  Optional<List<AssignmentQuestion>> findAllByAssignmentId(Long assignmentId);
}
