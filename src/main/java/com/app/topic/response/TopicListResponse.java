package com.app.topic.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicListResponse {
  private Integer pageNo;
  private Integer pageSize;
  private Integer totalPages;
  private Long totalRecords;
  private Boolean isLast;
  private List<TopicResponse> topicsList;
}
