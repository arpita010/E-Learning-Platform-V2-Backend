package com.app.studentDashboard;

import com.app.course.CourseService;
import com.app.course.response.CourseListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentDashboardService {
  private final CourseService courseService;

  public CourseListResponse getAllCourses(String queryName, Integer pageNo, Integer pageSize)
      throws Exception {
    return courseService.getAllCourses(queryName, pageNo, pageSize);
  }
}
