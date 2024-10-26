package com.app.topic;

import com.app.commons.SuperResponse;
import com.app.enums.ResponseStatus;
import com.app.topic.request.TopicRequest;
import com.app.topic.response.TopicListResponse;
import com.app.topic.response.TopicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/topic")
@RequiredArgsConstructor
public class TopicController {
  private final TopicService topicService;

  @PostMapping("/create")
  public TopicResponse create(@RequestBody TopicRequest topicRequest) throws Exception {
    return topicService.create(topicRequest);
  }

  @GetMapping("/classroom/{classroomId}/fetchAll")
  public TopicListResponse findAllByClassroomId(
      @PathVariable Long classroomId,
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize)
      throws Exception {
    return topicService.findAllbyClassroomId(classroomId, pageNo, pageSize);
  }

  @GetMapping("/{topicId}/fetch")
  public TopicResponse findByID(@PathVariable Long topicId) throws Exception {
    return topicService.findById(topicId);
  }

  @PostMapping("/{topicId}/update")
  public TopicResponse update(@PathVariable Long topicId, @RequestBody TopicRequest request)
      throws Exception {
    return topicService.update(topicId, request);
  }

  @GetMapping("/{topicId}/delete")
  public SuperResponse delete(@PathVariable Long topicId) throws Exception {
    topicService.delete(topicId);
    return new SuperResponse(ResponseStatus.SUCCESS);
  }
}
