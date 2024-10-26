package com.app.course.response;

import com.app.commons.SuperResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseListResponse extends SuperResponse {
  private Integer totalPages;
  private Integer pageNo;
  private Integer pageSize;
  private Long totalRecords;
  private Boolean isLastPage;
  private List<CourseResponse> courses;
}
