package com.app.announcement;

import com.app.announcement.request.AnnouncementRequest;
import com.app.announcement.response.AnnouncementListResponse;
import com.app.announcement.response.AnnouncementResponse;
import com.app.announcementRecord.AnnouncementRecord;
import com.app.announcementRecord.AnnouncementRecordService;
import com.app.classroom.Classroom;
import com.app.classroom.ClassroomService;
import com.app.comments.Comment;
import com.app.comments.CommentService;
import com.app.comments.request.CommentRequest;
import com.app.comments.response.CommentListResponse;
import com.app.comments.response.CommentResponse;
import com.app.user.User;
import com.app.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
  private final AnnouncementRepo announcementRepo;
  private final ClassroomService classroomService;
  private final UserService userService;
  private final CommentService commentService;
  private final AnnouncementRecordService announcementRecordService;

  public Announcement create(AnnouncementRequest request, String creatorEmail) throws Exception {
    Classroom classroom = classroomService.getById(request.getClassroomId());
    List<User> instructors = classroom.getInstructors();
    User user = userService.findByEmail(creatorEmail);
    Announcement announcement = request.toAnnouncement();
    announcement.setCreatorId(user.getId());
    announcement.setClassroomId(classroom.getId());
    Announcement saved = announcementRepo.save(announcement);
    createAnnouncementRecord(
        saved, null != instructors ? instructors.stream().map(User::getEmail).toList() : null);
    createAnnouncementRecord(saved, request.getPeoples());
    return saved;
  }

  private void createAnnouncementRecord(Announcement announcement, List<String> emailList) {
    if (emailList == null || emailList.isEmpty()) return;
    for (String email : emailList) {
      AnnouncementRecord record = new AnnouncementRecord();
      record.setAnnouncementId(announcement.getId());
      record.setUserEmail(email);
      record.setClassroomId(announcement.getClassroomId());
      announcementRecordService.save(record);
    }
  }

  public CommentResponse createComment(CommentRequest request, String email) throws Exception {
    User user = userService.findByEmail(email);
    Comment comment = request.toComment();
    comment.setUserId(user.getId());
    Comment saved = commentService.save(comment);
    return new CommentResponse(saved, user);
  }

  public Announcement getById(Long announcementId) throws Exception {
    Optional<Announcement> announcement = announcementRepo.findById(announcementId);
    if (announcement.isEmpty()) throw new Exception("Invalid Announcement ID");
    return announcement.get();
  }

  public CommentListResponse fetchAllComments(Long announcementId) throws Exception {
    Announcement announcement = getById(announcementId);
    List<Comment> commentList = commentService.fetchAllByPostId(announcement.getId());
    List<CommentResponse> responses = new ArrayList<>();
    if (null != commentList) {
      for (Comment comment : commentList) {
        User user = userService.getById(comment.getUserId());
        CommentResponse response = new CommentResponse(comment, user);
        responses.add(response);
      }
    }

    return CommentListResponse.builder()
        .totalRecords(1l * responses.size())
        .commentsList(responses)
        .build();
  }

  public void deleteCommentById(Long commentId) throws Exception {
    commentService.deleteById(commentId);
  }

  public void deleteById(Long announcementId) throws Exception {
    Optional<Announcement> opt = announcementRepo.findById(announcementId);
    if (opt.isEmpty()) throw new Exception("Invalid Announcement ID");
    Announcement announcement = opt.get();
    commentService.deleteAllByPostId(announcement.getId());
    announcementRecordService.deleteAllByAnnouncementId(announcement.getId());
    announcementRepo.delete(announcement);
  }

  public AnnouncementListResponse fetchAllByClassroom(
      Long classroomId, Integer pageNo, Integer pageSize) throws Exception {
    Pageable paging = PageRequest.of(pageNo, pageSize);
    Page<Announcement> announcementPage =
        announcementRepo.findAllByClassroomIdOrderByCreatedAtDesc(classroomId, paging);
    List<AnnouncementResponse> announcementResponses = new ArrayList<>();
    if (announcementPage.hasContent()) {
      for (Announcement announcement : announcementPage.getContent()) {
        User creator = userService.getById(announcement.getCreatorId());
        AnnouncementResponse response = new AnnouncementResponse(announcement, creator);
        announcementResponses.add(response);
      }
    }
    return AnnouncementListResponse.builder()
        .pageNo(announcementPage.getNumber())
        .pageSize(announcementPage.getSize())
        .totalPages(announcementPage.getTotalPages())
        .totalRecords(announcementPage.getTotalElements())
        .isLast(announcementPage.isLast())
        .announcementResponses(announcementResponses)
        .build();
  }

  public AnnouncementListResponse fetchAllByUserAndClassroom(
      String email, Long classroomId, Integer pageNo, Integer pageSize) throws Exception {
    Page<AnnouncementRecord> page =
        announcementRecordService.findAllByUserAndClassroom(email,classroomId, pageNo, pageSize);
    List<AnnouncementResponse> responses = new ArrayList<>();
    if (page.hasContent()) {
      for (AnnouncementRecord record : page.getContent()) {
        Announcement announcement = getById(record.getAnnouncementId());
        User creator = userService.getById(announcement.getCreatorId());
        AnnouncementResponse response = new AnnouncementResponse(announcement, creator);
        responses.add(response);
      }
    }
    return AnnouncementListResponse.builder()
        .totalPages(page.getTotalPages())
        .totalRecords(page.getTotalElements())
        .pageNo(pageNo)
        .pageSize(pageSize)
        .isLast(page.isLast())
        .announcementResponses(responses)
        .build();
  }

  public void updateComment(Long commentId, CommentRequest request) throws Exception {
    Comment comment = commentService.getById(commentId);
    if (request.getComment() != null) comment.setComment(request.getComment());
    commentService.save(comment);
  }
}
