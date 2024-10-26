package com.app.classroom.response;

import com.app.commons.SuperResponse;
import com.app.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstructorsListResponse extends SuperResponse {
  private Long totalCount;
  private List<UserResponse> instructorsList;
}
