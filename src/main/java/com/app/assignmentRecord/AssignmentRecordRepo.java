package com.app.assignmentRecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRecordRepo extends CrudRepository<AssignmentRecord, Long> {
  Page<AssignmentRecord> findAllByUserEmailOrderByCreatedAtDesc(String userEmail, Pageable paging);

  Page<AssignmentRecord> findAllByAssignmentIdOrderByCreatedAtDesc(
      Long assignmentId, Pageable paging);
}
