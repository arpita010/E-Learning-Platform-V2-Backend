package com.app.instructorDashboard.response;

import com.app.commons.SuperResponse;
import com.app.course.response.CourseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstructorDashboardSummary extends SuperResponse {
  private Long totalCourses;
  private Long totalStudents;
  private Long totalPurchasedCourses;
  private List<CourseResponse> top5CoursesList;
}
