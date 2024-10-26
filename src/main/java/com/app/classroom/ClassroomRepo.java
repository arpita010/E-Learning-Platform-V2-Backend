package com.app.classroom;

import com.app.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ClassroomRepo extends CrudRepository<Classroom, Long> {
  Page<Classroom> findAllByInstructorsContainingAndNameLikeIgnoreCaseOrderByCreatedAtDesc(
      Set<User> instructors, String name, Pageable paging);

  Page<Classroom> findAllByStudentsContainingAndNameLikeIgnoreCaseOrderByCreatedAtDesc(
      Set<User> students, String name, Pageable paging);
}
