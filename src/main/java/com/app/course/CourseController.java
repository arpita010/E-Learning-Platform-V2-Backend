package com.app.course;

import com.app.commons.SuperResponse;
import com.app.course.request.CourseRequest;
import com.app.course.response.CourseListResponse;
import com.app.course.response.CourseResponse;
import com.app.enums.FileType;
import com.app.enums.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/v1/course")
@RequiredArgsConstructor
public class CourseController {
  private final CourseService courseService;

  @PostMapping("/create")
  public CourseResponse create(@RequestBody CourseRequest request) throws Exception {
    return courseService.create(request);
  }

  @PostMapping("/{courseId}/upload/image")
  public CourseResponse uploadCourseImage(
      @PathVariable("courseId") Long courseId,
      @RequestParam("file") MultipartFile file,
      @RequestParam("fileType") FileType fileType,
      @RequestParam("fileName") String fileName)
      throws Exception {
    String exactFileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
    return courseService.uploadCourseImageToServer(courseId, file, exactFileName, fileType);
  }

  @PostMapping("/{courseId}/update")
  public CourseResponse update(
      @RequestBody CourseRequest request, @PathVariable(value = "courseId") Long courseId)
      throws Exception {
    return courseService.update(courseId, request);
  }

  @GetMapping("/{courseId}/fetch")
  public CourseResponse getById(@PathVariable Long courseId) throws Exception {
    return courseService.getByID(courseId);
  }

  @GetMapping("/user/fetchAll")
  public CourseListResponse fetchAllByUser(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "") String queryName)
      throws Exception {
    String email = MDC.get("email");
    return courseService.fetchAll(email, queryName, pageNo, pageSize);
  }

  @GetMapping("/fetchAll")
  public CourseListResponse fetchAll(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "") String queryName)
      throws Exception {
    return courseService.getAllCourses(queryName, pageNo, pageSize);
  }

  @PostMapping("/{courseId}/delete")
  public SuperResponse deleteById(@PathVariable Long courseId) throws Exception {
    courseService.delete(courseId);
    return new SuperResponse(ResponseStatus.SUCCESS);
  }
}
