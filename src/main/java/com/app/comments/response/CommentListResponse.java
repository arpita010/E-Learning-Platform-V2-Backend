package com.app.comments.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentListResponse {
  private Long totalRecords;
  private List<CommentResponse> commentsList;
}
