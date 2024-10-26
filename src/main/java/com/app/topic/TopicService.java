package com.app.topic;

import com.app.topic.request.TopicRequest;
import com.app.topic.response.TopicListResponse;
import com.app.topic.response.TopicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicService {
  private final TopicRepo topicRepo;

  public TopicResponse create(TopicRequest topicRequest) throws Exception {
    Topic topic = topicRequest.toTopic();
    Topic saved = topicRepo.save(topic);
    return new TopicResponse(saved);
  }

  public Topic getById(Long topicId) throws Exception {
    Topic topic = topicRepo.findById(topicId).orElse(null);
    return topic;
  }

  public TopicResponse findById(Long topicId) throws Exception {
    Optional<Topic> opt = topicRepo.findById(topicId);
    if (opt.isEmpty()) throw new Exception("Invalid Topic ID");
    return new TopicResponse(opt.get());
  }

  public TopicListResponse findAllbyClassroomId(Long classroomId, Integer pageNo, Integer pageSize)
      throws Exception {
    Pageable paging = PageRequest.of(pageNo, pageSize);
    Page<Topic> topicPage =
        topicRepo.findAllByClassroomIdOrderByCreatedAtDesc(classroomId, paging);
    List<TopicResponse> responses = new ArrayList<>();
    if (!topicPage.isEmpty() && !topicPage.getContent().isEmpty()) {
      for (Topic topic : topicPage.getContent()) {
        responses.add(new TopicResponse(topic));
      }
    }
    return TopicListResponse.builder()
        .pageNo(topicPage.getNumber())
        .pageSize(topicPage.getSize())
        .totalPages(topicPage.getTotalPages())
        .totalRecords(topicPage.getTotalElements())
        .isLast(topicPage.isLast())
        .topicsList(responses)
        .build();
  }

  public void delete(Long topicId) throws Exception {
    Optional<Topic> opt = topicRepo.findById(topicId);
    if (opt.isEmpty())
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid Topic ID");
    topicRepo.delete(opt.get());
  }

  public TopicResponse update(Long topicId, TopicRequest request) throws Exception {
    Optional<Topic> opt = topicRepo.findById(topicId);
    if (opt.isEmpty()) throw new Exception("Invalid Topic ID");
    Topic old = opt.get();
    if (request.getName() != null) old.setName(request.getName());
    Topic updated = topicRepo.save(old);
    return new TopicResponse(updated);
  }
}
