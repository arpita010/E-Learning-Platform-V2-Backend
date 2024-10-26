package com.app.feedback.response;

import com.app.commons.SuperResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackResponseList extends SuperResponse {
  private Integer pageNo;
  private Integer pageSize;
  private Long totalRecords;
  private Integer totalPages;
  private Boolean isLast;
  private List<FeedbackResponse> feedbacks;
}
