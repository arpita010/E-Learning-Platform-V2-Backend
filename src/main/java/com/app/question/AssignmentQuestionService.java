package com.app.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentQuestionService {
  private final AssignmentQuestionRepo questionRepo;

  public AssignmentQuestion save(AssignmentQuestion question) {
    return questionRepo.save(question);
  }

  public List<AssignmentQuestion> findAllByAssignment(Long assignmentId) throws Exception {
    Optional<List<AssignmentQuestion>> opt = questionRepo.findAllByAssignmentId(assignmentId);
    if (opt.isEmpty()) throw new Exception("No questions found");
    return opt.get();
  }
}
