package com.app.assignmentRecord;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentRecordService {
  private final AssignmentRecordRepo assignmentRecordRepo;

  public AssignmentRecord save(AssignmentRecord record) {
    return assignmentRecordRepo.save(record);
  }

  public Page<AssignmentRecord> fetchAllByUser(String userEmail, Integer pageNo, Integer pageSize) {
    Pageable paging = PageRequest.of(pageNo, pageSize);
    Page<AssignmentRecord> page =
        assignmentRecordRepo.findAllByUserEmailOrderByCreatedAtDesc(userEmail, paging);
    return page;
  }

  public Page<AssignmentRecord> fetchAllByAssignmentId(
      Long assignmentId, Integer pageNo, Integer pageSize) {
    Pageable paging = PageRequest.of(pageNo, pageSize);
    Page<AssignmentRecord> page =
        assignmentRecordRepo.findAllByAssignmentIdOrderByCreatedAtDesc(assignmentId, paging);
    return page;
  }
}
