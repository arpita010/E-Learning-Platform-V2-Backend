package com.app.feedback;

import com.app.course.CourseRepo;
import com.app.course.CourseService;
import com.app.feedback.request.FeedbackRequest;
import com.app.feedback.response.FeedbackResponse;
import com.app.feedback.response.FeedbackResponseList;
import com.app.user.User;
import com.app.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackService {
  private final FeedbackRepo feedbackRepo;
  private final UserService userService;
  private final CourseService courseService;

  public FeedbackResponse create(FeedbackRequest request) throws Exception {
    Feedback feedback = request.toFeedback();
    feedback.setPostedBy(MDC.get("email"));
    Feedback saved = feedbackRepo.save(feedback);
    return new FeedbackResponse(saved);
  }

  public FeedbackResponseList fetchAllByUser(String email, Integer pageNo, Integer pageSize)
      throws Exception {
    Pageable paging = PageRequest.of(pageNo, pageSize);
    Page<Feedback> page = feedbackRepo.findAllByPostedByOrderByCreatedAtDesc(email, paging);
    List<FeedbackResponse> responseList = new ArrayList<>();
    if (page.hasContent()) {
      for (Feedback feedback : page.getContent()) {
        responseList.add(new FeedbackResponse(feedback));
      }
    }

    return FeedbackResponseList.builder()
        .pageNo(page.getNumber())
        .pageSize(page.getSize())
        .totalPages(page.getTotalPages())
        .totalRecords(page.getTotalElements())
        .isLast(page.isLast())
        .feedbacks(responseList)
        .build();
  }

  public FeedbackResponseList fetchAllByCourse(Long courseId, Integer pageNo, Integer pageSize)
      throws Exception {
    Pageable paging = PageRequest.of(pageNo, pageSize);
    Page<Feedback> page = feedbackRepo.findAllByCourseIdOrderByCreatedAtDesc(courseId, paging);
    List<FeedbackResponse> responseList = new ArrayList<>();
    if (page.hasContent()) {
      for (Feedback feedback : page.getContent()) {
        responseList.add(new FeedbackResponse(feedback));
      }
    }

    return FeedbackResponseList.builder()
        .pageNo(page.getNumber())
        .pageSize(page.getSize())
        .totalPages(page.getTotalPages())
        .totalRecords(page.getTotalElements())
        .isLast(page.isLast())
        .feedbacks(responseList)
        .build();
  }

  public FeedbackResponse fetchById(Long feedbackId) throws Exception {
    Optional<Feedback> opt = feedbackRepo.findById(feedbackId);
    if (opt.isEmpty()) throw new Exception("Invalid Feedback ID");
    return new FeedbackResponse(opt.get());
  }

  public void deleteById(Long feedbackId) throws Exception {
    Optional<Feedback> opt = feedbackRepo.findById(feedbackId);
    if (opt.isEmpty()) throw new Exception("Invalid Feedback ID");
    feedbackRepo.delete(opt.get());
  }

  public Long getTotalCountByCourseId(Long courseId) {
    Long count = feedbackRepo.countByCourseId(courseId);
    return count;
  }

  public FeedbackResponseList fetchAllFeedbacksForInstructor(Integer pageNo, Integer pageSize) throws Exception {
    User user = userService.findByEmail(MDC.get("email"));
    List<Long> courseIdList = courseService.findAllCourseIdsByInstructor(user.getId());
    Pageable paging = PageRequest.of(pageNo, pageSize);
    Page<Feedback> page =
        feedbackRepo.findAllByCourseIdInOrderByCreatedAtDesc(courseIdList, paging);
    List<FeedbackResponse> list = new ArrayList<>();
    if (page.hasContent()) {
      for (Feedback feedback : page.getContent()) {
        FeedbackResponse response = new FeedbackResponse(feedback);
        list.add(response);
      }
    }

    return FeedbackResponseList.builder()
        .pageNo(page.getNumber())
        .pageSize(page.getSize())
        .totalPages(page.getTotalPages())
        .totalRecords(page.getTotalElements())
        .feedbacks(list)
        .isLast(page.isLast())
        .build();
  }
}
