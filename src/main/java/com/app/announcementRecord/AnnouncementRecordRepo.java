package com.app.announcementRecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRecordRepo extends CrudRepository<AnnouncementRecord, Long> {
  Page<AnnouncementRecord> findAllByClassroomIdOrderByCreatedAtDesc(
      Long classroomId, Pageable paging);

  Page<AnnouncementRecord> findAllByUserEmailAndClassroomIdOrderByCreatedAtDesc(
      String userEmail, Long classroomId, Pageable paging);

  List<AnnouncementRecord> findAllByAnnouncementId(Long announcementId);
}
