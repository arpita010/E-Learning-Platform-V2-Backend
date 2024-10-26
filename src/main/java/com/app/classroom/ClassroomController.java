package com.app.classroom;

import com.app.classroom.request.ClassroomJoinRequest;
import com.app.classroom.request.ClassroomRequest;
import com.app.classroom.response.ClassroomListResponse;
import com.app.classroom.response.ClassroomResponse;
import com.app.classroom.response.InstructorsListResponse;
import com.app.classroom.response.StudentsListResponse;
import com.app.commons.SuperResponse;
import com.app.enums.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/classroom")
public class ClassroomController {
  private final ClassroomService classroomService;

  @PostMapping("/create")
  public ClassroomResponse create(@RequestBody ClassroomRequest request) throws Exception {
    return classroomService.create(request);
  }

  @PostMapping("/{classroomId}/update")
  public ClassroomResponse update(
      @PathVariable Long classroomId, @RequestBody ClassroomRequest request) throws Exception {
    return classroomService.update(classroomId, request);
  }

  @PostMapping("/{classroomId}/delete")
  public SuperResponse delete(@PathVariable Long classroomId) throws Exception {
    classroomService.deleteById(classroomId);
    return new SuperResponse(ResponseStatus.SUCCESS);
  }

  // init request need to be created for the same operation to trigger email
  @PostMapping("/{classroomId}/user/join/init")
  public SuperResponse addUserToClassroom(
      @PathVariable Long classroomId, @RequestBody ClassroomJoinRequest request) throws Exception {
    classroomService.addUserToClassroom(classroomId, request);
    return new SuperResponse(ResponseStatus.SUCCESS);
  }

  @PostMapping("/{classroomId}/user/{userId}/join")
  public SuperResponse validateInstructorJoinRequest(
      @PathVariable Long classroomId, @PathVariable Long userId) throws Exception {
    classroomService.validateUserJoinRequestToClassroom(classroomId, userId);
    return new SuperResponse(ResponseStatus.SUCCESS);
  }

  @GetMapping("/{classroomId}/fetch")
  public ClassroomResponse getByClassroomId(@PathVariable Long classroomId) throws Exception {
    return classroomService.fetchById(classroomId);
  }

  @GetMapping("/fetchAll")
  public ClassroomListResponse fetchAll(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "") String queryName)
      throws Exception {
    String email = MDC.get("email");
    return classroomService.fetchAll(email, queryName, pageNo, pageSize);
  }

  @GetMapping("/{classroomId}/student/fetchAll")
  public StudentsListResponse fetchAllStudentsByClassroom(
      @PathVariable Long classroomId, @RequestParam(defaultValue = "") String queryName)
      throws Exception {
    return classroomService.fetchStudentsByClassroom(classroomId, queryName);
  }

  @GetMapping("/{classroomId}/instructor/fetchAll")
  public InstructorsListResponse fetchAllInstructorsByClassroom(
      @PathVariable Long classroomId, @RequestParam(defaultValue = "") String queryName)
      throws Exception {
    return classroomService.fetchInstructorsByClassroom(classroomId, queryName);
  }
}
