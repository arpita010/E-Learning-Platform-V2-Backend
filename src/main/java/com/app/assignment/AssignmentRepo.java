package com.app.assignment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepo extends CrudRepository<Assignment, Long> {
  Page<Assignment> findAllByClassroomIdOrderByCreatedAtDesc(Long classroomId, Pageable paging);
}
