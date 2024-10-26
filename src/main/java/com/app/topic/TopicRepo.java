package com.app.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepo extends CrudRepository<Topic, Long> {
  Page<Topic> findAllByClassroomIdOrderByCreatedAtDesc(Long classroomId, Pageable paging);
}
