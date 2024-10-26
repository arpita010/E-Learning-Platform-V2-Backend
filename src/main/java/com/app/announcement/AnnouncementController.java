package com.app.announcement;

import com.app.announcement.request.AnnouncementRequest;
import com.app.announcement.response.AnnouncementListResponse;
import com.app.announcement.response.AnnouncementResponse;
import com.app.comments.request.CommentRequest;
import com.app.comments.response.CommentListResponse;
import com.app.comments.response.CommentResponse;
import com.app.commons.SuperResponse;
import com.app.enums.ResponseStatus;
import com.app.user.User;
import com.app.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/announcement")
@RequiredArgsConstructor
@Slf4j
public class AnnouncementController {
  private final AnnouncementService announcementService;
  private final UserService userService;

  @PostMapping("/create")
  public AnnouncementResponse create(@RequestBody AnnouncementRequest request) throws Exception {
    log.info("Announcement Request  : {}", request);
    String email = MDC.get("email");
    Announcement announcement = announcementService.create(request, email);
    // list of comments will also be there.
    User user = userService.findByEmail(email);
    return new AnnouncementResponse(announcement, user);
  }

  @PostMapping("/comment/post")
  public CommentResponse postComment(@RequestBody CommentRequest request) throws Exception {
    String email = MDC.get("email");
    return announcementService.createComment(request, email);
  }

  @GetMapping("/{announcementId}/comment/fetchAll")
  public CommentListResponse getAllComments(@PathVariable Long announcementId) throws Exception {
    return announcementService.fetchAllComments(announcementId);
  }

  @GetMapping("/comment/{commentId}/delete")
  public SuperResponse deleteCommentById(@PathVariable Long commentId) throws Exception {
    announcementService.deleteCommentById(commentId);
    return new SuperResponse(ResponseStatus.SUCCESS);
  }

  @PostMapping("/comment/{commentId}/update")
  public SuperResponse updateComment(
      @PathVariable Long commentId, @RequestBody CommentRequest request) throws Exception {
    log.info("inside it");
    announcementService.updateComment(commentId, request);
    return new SuperResponse(ResponseStatus.SUCCESS);
  }

  @GetMapping("/{announcementId}/delete")
  public SuperResponse deleteAnnouncementById(@PathVariable Long announcementId) throws Exception {
    announcementService.deleteById(announcementId);
    return new SuperResponse(ResponseStatus.SUCCESS);
  }

  @GetMapping("/classroom/{classroomId}/fetchAll")
  public AnnouncementListResponse fetchAllByClassroom(
      @PathVariable Long classroomId,
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize)
      throws Exception {
    return announcementService.fetchAllByClassroom(classroomId, pageNo, pageSize);
  }

  @GetMapping("/classroom/{classroomId}/user/fetchAll")
  public AnnouncementListResponse fetchAllByUser(
      @PathVariable Long classroomId,
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize)
      throws Exception {
    String email = MDC.get("email");
    return announcementService.fetchAllByUserAndClassroom(email, classroomId, pageNo, pageSize);
  }
}
