package com.app.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepo extends CrudRepository<Feedback, Long> {
  Page<Feedback> findAllByPostedByOrderByCreatedAtDesc(String postedBy, Pageable paging);

  Page<Feedback> findAllByCourseIdOrderByCreatedAtDesc(Long courseId, Pageable paging);

  Long countByCourseId(Long courseId);

  Page<Feedback> findAllByCourseIdInOrderByCreatedAtDesc(List<Long> courseIdList, Pageable paging);
}
