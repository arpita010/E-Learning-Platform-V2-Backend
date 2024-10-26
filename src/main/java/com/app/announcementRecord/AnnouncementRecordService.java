package com.app.announcementRecord;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementRecordService {
  private final AnnouncementRecordRepo announcementRecordRepo;

  public AnnouncementRecord save(AnnouncementRecord record) {
    return announcementRecordRepo.save(record);
  }

  public Page<AnnouncementRecord> findAllByUserAndClassroom(
      String userEmail, Long classroomId, Integer pageNo, Integer pageSize) {
    Pageable paging = PageRequest.of(pageNo, pageSize);
    Page<AnnouncementRecord> page =
        announcementRecordRepo.findAllByUserEmailAndClassroomIdOrderByCreatedAtDesc(
            userEmail, classroomId, paging);
    return page;
  }

  public void delete() {}

  public void deleteAllByAnnouncementId(Long announcementId) {
    List<AnnouncementRecord> records =
        announcementRecordRepo.findAllByAnnouncementId(announcementId);
    announcementRecordRepo.deleteAll(records);
  }
}
