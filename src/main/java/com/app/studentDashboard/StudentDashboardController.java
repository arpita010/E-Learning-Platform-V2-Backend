package com.app.studentDashboard;

import com.app.course.response.CourseListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/dashboard/student")
public class StudentDashboardController {
  private final StudentDashboardService studentDashboardService;

  @GetMapping("/course/fetchAll")
  public CourseListResponse getAllCourses(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "") String queryName)
      throws Exception {
    return studentDashboardService.getAllCourses(queryName, pageNo, pageSize);
  }
}
