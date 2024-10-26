package com.app.announcement.response;

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
public class AnnouncementListResponse extends SuperResponse {
  private Integer pageNo;
  private Integer pageSize;
  private Integer totalPages;
  private Long totalRecords;
  private Boolean isLast;
  private List<AnnouncementResponse> announcementResponses;
}
