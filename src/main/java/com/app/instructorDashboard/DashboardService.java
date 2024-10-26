package com.app.instructorDashboard;

import com.app.course.CourseService;
import com.app.course.response.CourseListResponse;
import com.app.course.response.CourseResponse;
import com.app.instructorDashboard.response.InstructorDashboardSummary;
import com.app.purchaseRecord.CoursePurchaseRecordRepo;
import com.app.purchaseRecord.CoursePurchaseRecordService;
import com.app.user.User;
import com.app.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@Service
@RequiredArgsConstructor
public class DashboardService {
  private final UserService userService;
  private final CourseService courseService;
  private final CoursePurchaseRecordService coursePurchaseRecordService;

  public InstructorDashboardSummary getInstructorDashboardSummary(String email) throws Exception {
    User user = userService.findByEmail(email);
    List<Long> courseIdList = courseService.findAllCourseIdsByInstructor(user.getId());
    List<Long> top5CourseIds = fetchTop5CourseIds(courseIdList);
    InstructorDashboardSummary dashboardSummary = new InstructorDashboardSummary();
    List<CourseResponse> top5Courses = new ArrayList<>();
    for (Long id : top5CourseIds) {
      CourseResponse response = courseService.getByID(id);
      top5Courses.add(response);
    }
    Long totalStudents = 0L;
    Long totalPurchasedCourses = 0L;
    for (Long courseId : courseIdList) {
      Long count = coursePurchaseRecordService.getPurchaseCountByCourseId(courseId);
      if (count > 0) totalPurchasedCourses++;
      totalStudents += count;
    }
    dashboardSummary.setTotalCourses((long) courseIdList.size());
    dashboardSummary.setTop5CoursesList(top5Courses);
    dashboardSummary.setTotalStudents(totalStudents);
    dashboardSummary.setTotalPurchasedCourses(totalPurchasedCourses);
    return dashboardSummary;
  }

  public List<Long> fetchTop5CourseIds(List<Long> courseIdList) {
    List<Long> topCourses = new ArrayList<>();
    PriorityQueue<Long[]> queue = new PriorityQueue<>((a, b) -> (int) (a[1] - b[1]));

    for (Long id : courseIdList) {
      Long count = coursePurchaseRecordService.getPurchaseCountByCourseId(id);
      queue.add(new Long[] {id, count});
      if (queue.size() > 5) {
        queue.poll();
      }
    }

    while (!queue.isEmpty()) {
      topCourses.add(queue.poll()[0]);
    }

    return topCourses;
  }
}
