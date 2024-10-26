package com.app.course;

import com.app.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepo extends CrudRepository<Course, Long> {
  Page<Course> findAllByIsActiveIsTrueAndInstructorAndNameLikeIgnoreCaseOrderByCreatedAtDesc(
      User instructor, String name, Pageable pageable);

  Page<Course> findAllByIsActiveIsTrueAndNameLikeIgnoreCaseOrderByCreatedAtDesc(
      String name, Pageable paging);

  @Query(
      value = "select id from course where instructor_id=:instructorId and is_active=1;",
      nativeQuery = true)
  Optional<List<Long>> findAllCourseIdsByInstructorAndIsActiveTrue(Long instructorId);
}
