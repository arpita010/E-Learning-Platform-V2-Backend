package com.app.course;

import com.app.chapter.ChapterService;
import com.app.course.request.CourseRequest;
import com.app.course.response.CourseListResponse;
import com.app.course.response.CourseResponse;
import com.app.enums.FileType;
import com.app.exceptions.CourseException;
import com.app.filestore.FileStore;
import com.app.filestore.FileStoreService;
import com.app.purchaseRecord.CoursePurchaseRecord;
import com.app.purchaseRecord.CoursePurchaseRecordRepo;
import com.app.user.User;
import com.app.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
  private final CourseRepo courseRepo;
  private final UserService userService;
  private final FileStoreService fileStoreService;
  private final ChapterService chapterService;
  private final CoursePurchaseRecordRepo coursePurchaseRecordRepo;

  public CourseResponse create(CourseRequest request) throws Exception {
    Course course = request.toCourse();
    String email = MDC.get("email");
    course.setInstructor(userService.findByEmail(email));
    Course saved = courseRepo.save(course);
    return new CourseResponse(saved);
  }

  public CourseResponse update(Long courseId, CourseRequest request) throws Exception {
    Optional<Course> opt = courseRepo.findById(courseId);
    if (opt.isEmpty()) throw new CourseException("Invalid Course ID");
    Course course = opt.get();
    request.updateDetails(course);
    Course saved = courseRepo.save(course);
    return new CourseResponse(saved);
  }

  public CourseResponse getByID(Long courseId) throws Exception {
    Optional<Course> opt = courseRepo.findById(courseId);
    if (opt.isEmpty()) throw new CourseException("Invalid Course ID.");
    CourseResponse response = new CourseResponse(opt.get());
    response.setTotalChapters(chapterService.getTotalCountOfChapters(opt.get().getId()));
    response.setIsPurchased(checkIfCoursePurchased(MDC.get("email"), courseId));
    return response;
  }

  public CourseListResponse fetchAll(
      String email, String queryName, Integer pageNo, Integer pageSize) throws Exception {
    String queryInput = "%" + queryName + "%";
    Pageable paging = PageRequest.of(pageNo, pageSize);
    User instructor = userService.findByEmail(email);
    Page<Course> courseList =
        courseRepo.findAllByIsActiveIsTrueAndInstructorAndNameLikeIgnoreCaseOrderByCreatedAtDesc(
            instructor, queryInput, paging);
    List<CourseResponse> courseResponses = new ArrayList<>();
    if (!courseList.isEmpty() && !courseList.getContent().isEmpty()) {
      for (Course course : courseList.getContent()) {
        CourseResponse response = new CourseResponse(course);
        response.setTotalChapters(chapterService.getTotalCountOfChapters(course.getId()));
        response.setIsPurchased(checkIfCoursePurchased(email, course.getId()));
        courseResponses.add(response);
      }
    }
    return CourseListResponse.builder()
        .pageNo(courseList.getNumber())
        .pageSize(courseList.getSize())
        .totalPages(courseList.getTotalPages())
        .totalRecords(courseList.getTotalElements())
        .isLastPage(courseList.isLast())
        .courses(courseResponses)
        .build();
  }

  public CourseResponse uploadCourseImageToServer(
      Long courseId, MultipartFile file, String fileName, FileType fileType) throws Exception {
    Optional<Course> opt = courseRepo.findById(courseId);
    if (opt.isEmpty()) throw new CourseException("Invalid Course ID");
    FileStore fileStore = fileStoreService.uploadFileToServer(file, fileName, fileType, courseId);
    Course course = opt.get();
    course.setThumbnailUrl(null != fileStore ? fileStore.getGithubDownloadUrl() : null);
    Course saved = courseRepo.save(course);
    return new CourseResponse(saved);
  }

  public Course getById(Long courseId) throws Exception {
    Optional<Course> opt = courseRepo.findById(courseId);
    if (opt.isEmpty()) throw new CourseException("Invalid Course ID");
    return opt.get();
  }

  public Course save(Course course) {
    return courseRepo.save(course);
  }

  public void delete(Long couseId) throws Exception {
    Optional<Course> opt = courseRepo.findById(couseId);
    if (opt.isEmpty()) {
      throw new CourseException("Invalid Course ID");
    }
    Course course = opt.get();
    course.setActive(false);
    courseRepo.save(course);
  }

  public CourseListResponse getAllCourses(String queryName, Integer pageNo, Integer pageSize)
      throws Exception {
    String queryInput = "%" + queryName + "%";
    Pageable paging = PageRequest.of(pageNo, pageSize);
    Page<Course> courseList =
        courseRepo.findAllByIsActiveIsTrueAndNameLikeIgnoreCaseOrderByCreatedAtDesc(
            queryInput, paging);
    List<CourseResponse> courseResponses = new ArrayList<>();
    String email = MDC.get("email");
    if (!courseList.isEmpty() && !courseList.getContent().isEmpty()) {
      for (Course course : courseList.getContent()) {
        CourseResponse response = new CourseResponse(course);
        response.setTotalChapters(chapterService.getTotalCountOfChapters(course.getId()));
        response.setIsPurchased(checkIfCoursePurchased(email, course.getId()));
        courseResponses.add(response);
      }
    }
    return CourseListResponse.builder()
        .pageNo(courseList.getNumber())
        .pageSize(courseList.getSize())
        .totalPages(courseList.getTotalPages())
        .totalRecords(courseList.getTotalElements())
        .isLastPage(courseList.isLast())
        .courses(courseResponses)
        .build();
  }

  public Boolean checkIfCoursePurchased(String email, Long courseId) throws Exception {
    Optional<CoursePurchaseRecord> opt =
        coursePurchaseRecordRepo
            .findTopByCourseIdAndUserEmailAndExpiryDateIsNullOrExpiryDateAfterOrderByCreatedAtDesc(
                courseId, email, LocalDateTime.now());
    return opt.isPresent();
  }

  public List<Long> findAllCourseIdsByInstructor(Long instructorId) {
    Optional<List<Long>> opt = courseRepo.findAllCourseIdsByInstructorAndIsActiveTrue(instructorId);
    return opt.orElse(null);
  }
}
