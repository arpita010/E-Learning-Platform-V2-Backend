package com.app.assignment;

import com.app.assignment.request.AssignmentRequest;
import com.app.assignment.response.AssignmentListResponse;
import com.app.assignment.response.AssignmentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/assignment")
@RequiredArgsConstructor
public class AssignmentController {
  private final AssignmentService assignmentService;

  @PostMapping("/create")
  public AssignmentResponse create(@RequestBody AssignmentRequest request) throws Exception {
    log.info("Assignment Create Request : {}", request);
    return assignmentService.create(request);
  }

  @GetMapping("/classroom/{classroomId}/fetchAll")
  public AssignmentListResponse fetchAllByClassroomID(
      @PathVariable Long classroomId,
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize)
      throws Exception {
    return assignmentService.fetchAllByClassroomId(classroomId, pageNo, pageSize);
  }

  @GetMapping("/user/fetchAll")
  public AssignmentListResponse fetchAllByUser(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize)
      throws Exception {
    String email = MDC.get("email");
    return assignmentService.fetchAllByUser(email, pageNo, pageSize);
  }
}
