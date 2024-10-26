package com.app.chapter;

import com.app.course.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepo extends CrudRepository<Chapter, Long> {
  Page<Chapter> findAllByCourseAndNameLikeIgnoreCaseOrderByCreatedAtDesc(
      Course course, String name, Pageable paging);

  Long countByCourseId(Long courseId);
}
