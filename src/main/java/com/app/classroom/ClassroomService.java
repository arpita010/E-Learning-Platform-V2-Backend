package com.app.classroom;

import com.app.classroom.request.ClassroomJoinRequest;
import com.app.classroom.request.ClassroomRequest;
import com.app.classroom.response.ClassroomListResponse;
import com.app.classroom.response.ClassroomResponse;
import com.app.classroom.response.InstructorsListResponse;
import com.app.classroom.response.StudentsListResponse;
import com.app.email.EmailService;
import com.app.email.request.EmailSendRequest;
import com.app.enums.UserRole;
import com.app.exceptions.ClassroomException;
import com.app.shortenedUrl.ShortenedUrl;
import com.app.shortenedUrl.ShortenedUrlService;
import com.app.template.TemplateService;
import com.app.template.TemplateType;
import com.app.user.User;
import com.app.user.UserService;
import com.app.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClassroomService {
  private final ClassroomRepo classroomRepo;
  private final UserService userService;
  private final ShortenedUrlService shortenedUrlService;
  private final TemplateService templateService;
  private final EmailService emailService;

  @Value("${classroom.join.original.url}")
  private String classroomJoinOriginalUrl;

  @Value("${classroom.join.shortened.url}")
  private String classroomJoinShortUrl;

  public ClassroomResponse create(ClassroomRequest request) throws Exception {
    String email = MDC.get("email");
    User user = userService.findByEmail(email);
    if (!UserRole.INSTRUCTOR.equals(user.getRole()))
      throw new ClassroomException("Invalid Request");
    Classroom classroom = request.toClassroom();
    classroom.setCreator(user);
    classroom.setInstructors(new ArrayList<>(Arrays.asList(new User[] {user})));
    ClassroomResponse classroomResponse = new ClassroomResponse(classroomRepo.save(classroom));
    return classroomResponse;
  }

  public ClassroomResponse update(Long classroomId, ClassroomRequest request) throws Exception {
    Classroom classroom = getById(classroomId);
    request.updateDetails(classroom);
    Classroom saved = classroomRepo.save(classroom);
    return new ClassroomResponse(saved);
  }

  public void deleteById(Long classroomId) throws Exception {
    Classroom classroom = getById(classroomId);
    classroomRepo.delete(classroom);
  }

  public Classroom getById(Long classroomId) throws Exception {
    Optional<Classroom> opt = classroomRepo.findById(classroomId);
    if (opt.isEmpty()) throw new ClassroomException("Invalid Classroom ID");
    return opt.get();
  }

  public void addUserToClassroom(Long classroomId, ClassroomJoinRequest request) throws Exception {
    Classroom classroom = getById(classroomId);
    User user = userService.findByEmail(request.getEmail());
    // send email to user(student|instructor) with shortened url to join the classroom
    sendEmailToJoinClassroom(user, classroom);
  }

  @Async
  protected void sendEmailToJoinClassroom(User user, Classroom classroom) throws Exception {
    String originalUrl =
        classroomJoinOriginalUrl
            .replace("{userId}", String.valueOf(user.getId()))
            .replace("{classroomId}", String.valueOf(classroom.getId()));
    ShortenedUrl shortenedUrl = shortenedUrlService.create(originalUrl);
    String shortUrl =
        classroomJoinShortUrl.replace("{shortenedKey}", shortenedUrl.getShortenedValue());
    Map<String, String> data = new HashMap<>();
    data.put("shortUrl", shortUrl);
    data.put("username", user.getName());
    //    emailService.sendEmail(createEmailSendRequest(TemplateType.CLASSROOM_JOIN_INIT_EMAIL,
    // data));
  }

  private EmailSendRequest createEmailSendRequest(
      TemplateType templateType, Map<String, String> data) {
    String content = templateService.getPopulatedMessage(templateType, data);
    return EmailSendRequest.builder()
        .toList(List.of(""))
        .ccList(List.of(""))
        .subject(templateType.subject())
        .content(content)
        .build();
  }

  public void validateUserJoinRequestToClassroom(Long classroomId, Long userId) throws Exception {
    Classroom classroom = getById(classroomId);
    User user = userService.getById(userId);
    switch (user.getRole()) {
      case INSTRUCTOR -> {
        if (classroom.getInstructors() == null) {
          classroom.setInstructors(new ArrayList<>());
        }
        classroom.getInstructors().add(user);
        classroom.addInstructor(user);
      }
      case STUDENT -> {
        if (classroom.getStudents() == null) {
          classroom.setStudents(new ArrayList<>());
        }
        classroom.addStudent(user);
      }
    }
    Classroom saved = classroomRepo.save(classroom);
  }

  public ClassroomResponse fetchById(Long classroomId) throws Exception {
    Optional<Classroom> opt = classroomRepo.findById(classroomId);
    if (opt.isEmpty()) {
      throw new ClassroomException("Invalid Classroom ID");
    }
    return new ClassroomResponse(opt.get());
  }

  public ClassroomListResponse fetchAll(
      String email, String queryName, Integer pageNo, Integer pageSize) throws Exception {
    String queryInput = "%" + queryName + "%";
    User user = userService.findByEmail(email);
    Pageable paging = PageRequest.of(pageNo, pageSize);
    Page<Classroom> classroomPage = null;
    if (UserRole.INSTRUCTOR.equals(user.getRole())) {
      classroomPage =
          classroomRepo.findAllByInstructorsContainingAndNameLikeIgnoreCaseOrderByCreatedAtDesc(
              new HashSet<>(List.of(user)), queryInput, paging);
    }
    if (UserRole.STUDENT.equals(user.getRole())) {
      classroomPage =
          classroomRepo.findAllByStudentsContainingAndNameLikeIgnoreCaseOrderByCreatedAtDesc(
              new HashSet<>(List.of(user)), queryInput, paging);
    }

    List<ClassroomResponse> classroomResponses = new ArrayList<>();
    if (null != classroomPage && !classroomPage.isEmpty()) {
      classroomResponses = classroomPage.getContent().stream().map(ClassroomResponse::new).toList();
    }
    return ClassroomListResponse.builder()
        .pageNo(classroomPage.getNumber())
        .pageSize(classroomPage.getSize())
        .totalPages(classroomPage.getTotalPages())
        .totalRecords(classroomPage.getTotalElements())
        .isLast(classroomPage.isLast())
        .classroomResponses(classroomResponses)
        .build();
  }

  public StudentsListResponse fetchStudentsByClassroom(Long classroomId, String queryName)
      throws Exception {
    String queryInput = queryName.toLowerCase();
    Classroom classroom = getById(classroomId);
    List<User> students =
        classroom.getStudents().stream()
            .filter(st -> st.getName().toLowerCase().contains(queryInput))
            .toList();
    List<UserResponse> studentsList = students.stream().map(UserResponse::new).toList();
    return StudentsListResponse.builder()
        .totalCount((long) studentsList.size())
        .studentsList(studentsList)
        .build();
  }

  public InstructorsListResponse fetchInstructorsByClassroom(Long classroomId, String queryName)
      throws Exception {
    String queryInput = queryName.toLowerCase();
    Classroom classroom = getById(classroomId);
    List<User> instructors =
        classroom.getInstructors().stream()
            .filter(ins -> ins.getName().toLowerCase().contains(queryInput))
            .toList();
    List<UserResponse> instructorsList = instructors.stream().map(UserResponse::new).toList();
    return InstructorsListResponse.builder()
        .totalCount((long) instructorsList.size())
        .instructorsList(instructorsList)
        .build();
  }
}
