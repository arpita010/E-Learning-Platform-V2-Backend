package com.app.feedback;

import com.app.commons.SuperResponse;
import com.app.enums.ResponseStatus;
import com.app.feedback.request.FeedbackRequest;
import com.app.feedback.response.FeedbackResponse;
import com.app.feedback.response.FeedbackResponseList;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {
  private final FeedbackService feedbackService;

  @PostMapping("/create")
  public FeedbackResponse create(@RequestBody FeedbackRequest request) throws Exception {
    return feedbackService.create(request);
  }

  @GetMapping("/course/{courseId}/fetchAll")
  public FeedbackResponseList fetchAllByCourse(
      @PathVariable Long courseId,
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize)
      throws Exception {
    return feedbackService.fetchAllByCourse(courseId, pageNo, pageSize);
  }

  @GetMapping("/user/fetchAll")
  public FeedbackResponseList fetchAllByUser(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize)
      throws Exception {
    String email = MDC.get("email");
    return feedbackService.fetchAllByUser(email, pageNo, pageSize);
  }

  @GetMapping("/{feedbackId}/fetch")
  public FeedbackResponse fetchById(@PathVariable Long feedbackId) throws Exception {
    return feedbackService.fetchById(feedbackId);
  }

  @GetMapping("/{feedbackId}/delete")
  public SuperResponse deleteById(@PathVariable Long feedbackId) throws Exception {
    feedbackService.deleteById(feedbackId);
    return new SuperResponse(ResponseStatus.SUCCESS);
  }

  @GetMapping("/instructor/fetchAll")
  public FeedbackResponseList fetchAllFeedbacksForInstructor(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize)
      throws Exception {
    return feedbackService.fetchAllFeedbacksForInstructor(pageNo, pageSize);
  }
}
