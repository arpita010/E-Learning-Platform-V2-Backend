package com.app.instructorDashboard;

import com.app.instructorDashboard.response.InstructorDashboardSummary;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/dashboard")
public class DashboardController {
  private final DashboardService dashboardService;

  @GetMapping("/instructor/summary")
  public InstructorDashboardSummary getInstructorDashboardSummary() throws Exception {
    String email = MDC.get("email");
    return dashboardService.getInstructorDashboardSummary(email);
  }
}
