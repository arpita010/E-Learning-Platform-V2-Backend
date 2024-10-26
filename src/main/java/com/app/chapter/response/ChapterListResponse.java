package com.app.chapter.response;

import com.app.commons.SuperResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterListResponse extends SuperResponse {
  private Integer pageNo;
  private Integer pageSize;
  private Integer totalPages;
  private Long totalRecords;
  private Boolean isLastPage;
  private List<ChapterResponse> chapters;
}
