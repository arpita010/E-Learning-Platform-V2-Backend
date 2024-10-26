package com.app.user.response;

import com.app.commons.SuperResponse;
import com.app.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateResponse extends SuperResponse {
  private String email;
  private String authToken;
  private UserRole role;
}
