package com.app.purchaseRecord;

import com.app.chapter.ChapterService;
import com.app.course.Course;
import com.app.course.CourseService;
import com.app.course.response.CourseListResponse;
import com.app.course.response.CourseResponse;
import com.app.enums.PaymentStatus;
import com.app.payment.Payment;
import com.app.payment.PaymentService;
import com.app.purchaseRecord.request.CoursePurchaseRequest;
import com.app.purchaseRecord.response.PaymentRecordListResponse;
import com.app.purchaseRecord.response.PaymentRecordResponse;
import com.app.user.User;
import com.app.user.UserService;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoursePurchaseRecordService {
  private final CoursePurchaseRecordRepo coursePurchaseRecordRepo;
  private final CourseService courseService;
  private final UserService userService;
  private final PaymentService paymentService;
  private final ChapterService chapterService;

  public void purchaseCourse(CoursePurchaseRequest request) throws Exception {
    Course course = courseService.getById(request.getCourseId());
    User user = userService.findByEmail(request.getEmail());
    Payment payment = paymentService.getByID(request.getPaymentId());
    if (!PaymentStatus.SUCCESS.equals(payment.getPaymentStatus())) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Payment Failed.");
    }
    CoursePurchaseRecord coursePurchaseRecord = new CoursePurchaseRecord();
    coursePurchaseRecord.setCourseId(request.getCourseId());
    coursePurchaseRecord.setExpiryDate(
        getSubscriptionExpiryTime(course.getSubscriptionPlanTimeInMonths()));
    coursePurchaseRecord.setPaymentId(payment.getId());
    coursePurchaseRecord.setUserEmail(user.getEmail());
    coursePurchaseRecordRepo.save(coursePurchaseRecord);
    List<User> students = course.getStudentsEnrolledInList();
    students.add(user);
    courseService.save(course);
  }

  private Date getSubscriptionExpiryTime(Integer subscriptionPlanTimeInMonths) {
    if (null == subscriptionPlanTimeInMonths) return null;
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MONTH, subscriptionPlanTimeInMonths);
    return calendar.getTime();
  }

  public CourseListResponse getAllPurchasedCourses(
      String email, String queryName, Integer pageNo, Integer pageSize) throws Exception {
    String queryInput = "%" + queryName + "%";
    Pageable paging = PageRequest.of(pageNo, pageSize);
    Page<CoursePurchaseRecord> page =
        coursePurchaseRecordRepo.findAllByUserEmailOrderByCreatedAtDesc(email, paging);
    List<CourseResponse> courseResponseList = new ArrayList<>();
    if (!page.isEmpty() && !page.getContent().isEmpty()) {
      for (CoursePurchaseRecord record : page.getContent()) {
        CourseResponse course = courseService.getByID(record.getCourseId());
        course.setTotalChapters(chapterService.getTotalCountOfChapters(record.getCourseId()));
        course.setIsPurchased(checkIfCoursePurchased(email, record.getCourseId()));
        courseResponseList.add(course);
      }
    }
    return CourseListResponse.builder()
        .pageNo(page.getNumber())
        .pageSize(page.getSize())
        .totalRecords(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .courses(courseResponseList)
        .build();
  }

  public Boolean checkIfCoursePurchased(String email, Long courseId) {
    Optional<CoursePurchaseRecord> opt =
        coursePurchaseRecordRepo
            .findTopByCourseIdAndUserEmailAndExpiryDateIsNullOrExpiryDateAfterOrderByCreatedAtDesc(
                courseId, email, LocalDateTime.now());
    return opt.isPresent();
  }

  public PaymentRecordListResponse fetchAllPaymentRecords(Integer pageNo, Integer pageSize)
      throws Exception {
    String email = MDC.get("email");
    User user = userService.findByEmail(email);
    Pageable paging = PageRequest.of(pageNo, pageSize);
    Page<CoursePurchaseRecord> page =
        coursePurchaseRecordRepo.findAllByInstructorId(user.getId(), paging);
    List<PaymentRecordResponse> recordResponses = new ArrayList<>();
    if (page.hasContent()) {
      for (CoursePurchaseRecord record : page.getContent()) {
        Payment payment = paymentService.getByID(record.getPaymentId());
        Course course = courseService.getById(record.getCourseId());
        PaymentRecordResponse response =
            PaymentRecordResponse.builder()
                .paymentId(payment.getId())
                .paymentStatus(payment.getPaymentStatus())
                .userEmail(record.getUserEmail())
                .courseName(course.getName())
                .placedAt(record.getCreatedAt())
                .build();
        recordResponses.add(response);
      }
    }

    return PaymentRecordListResponse.builder()
        .pageNo(pageNo)
        .pageSize(pageSize)
        .totalPages(page.getTotalPages())
        .totalRecords(page.getTotalElements())
        .isLast(page.isLast())
        .paymentRecords(recordResponses)
        .build();
  }

  public File downloadAllPaymentRecordsFile() throws Exception {
    PaymentRecordListResponse response = fetchAllPaymentRecords(0, Integer.MAX_VALUE);
    File tempFile = Files.createTempFile("paymentRecords", ".csv").toFile();
    FileWriter fileWriter = new FileWriter(tempFile);
    CSVWriter csvWriter = new CSVWriter(fileWriter);
    String[] headers =
        new String[] {"Payment ID", "User Email", "Course Name", "Payment Status", "Creation Time"};
    csvWriter.writeNext(headers);
    log.info("Total Records : {}", response.getPaymentRecords().size());
    if (response != null && response.getPaymentRecords() != null) {
      for (PaymentRecordResponse record : response.getPaymentRecords()) {
        try {
          String[] nextRow =
              new String[] {
                String.valueOf(record.getPaymentId()),
                record.getUserEmail(),
                record.getCourseName(),
                String.valueOf(record.getPaymentStatus()),
                convertToRequiredDateFormat(record.getPlacedAt())
              };
          csvWriter.writeNext(nextRow);
        } catch (Exception e) {
          log.error("Error while writing record to Payment Record File : {}", e.getMessage());
        }
      }
    }
    csvWriter.flush();
    fileWriter.close();
    return tempFile;
  }

  private String convertToRequiredDateFormat(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:SS");
    String response = formatter.format(date);
    return response;
  }

  public Long getPurchaseCountByCourseId(Long courseId) {
    return coursePurchaseRecordRepo.countAllByCourseId(courseId);
  }
}
