package com.app.purchaseRecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CoursePurchaseRecordRepo extends CrudRepository<CoursePurchaseRecord, Long> {
  Page<CoursePurchaseRecord> findAllByUserEmailOrderByCreatedAtDesc(
      String userEmail, Pageable paging);

  Optional<CoursePurchaseRecord>
      findTopByCourseIdAndUserEmailAndExpiryDateIsNullOrExpiryDateAfterOrderByCreatedAtDesc(
          Long courseId, String userEmail, LocalDateTime now);

  Page<CoursePurchaseRecord> findAllByCourseIdInOrderByCreatedAtDesc(
      List<Long> coursesIdList, Pageable paging);

  @Query(
      value =
          " select cpr.id, cpr.created_at, cpr.updated_at, cpr.course_id,cpr.expiry_date,\n"
              + " cpr.payment_id,cpr.user_email from course c inner join course_purchase_record cpr on\n"
              + " c.id=cpr.course_id where c.instructor_id=:instructorId order by cpr.created_at desc;",
      countQuery =
          "SELECT COUNT(c.id) FROM course c \n"
              + "INNER JOIN course_purchase_record cpr ON c.id = cpr.course_id \n"
              + "WHERE c.instructor_id = :instructorId",
      nativeQuery = true)
  Page<CoursePurchaseRecord> findAllByInstructorId(Long instructorId, Pageable paging);

  Long countAllByCourseId(Long courseId);
}
