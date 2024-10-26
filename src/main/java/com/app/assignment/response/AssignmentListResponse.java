package com.app.assignment.response;

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
public class AssignmentListResponse extends SuperResponse {
  private Integer pageNo;
  private Integer pageSize;
  private Integer totalPages;
  private Long totalRecords;
  private Boolean isLast;
  private List<AssignmentTopicResponse> assignmentTopicResponses;
}
