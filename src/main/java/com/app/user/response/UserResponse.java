package com.app.user.response;

import com.app.enums.UserRole;
import com.app.user.User;
import com.app.user.UserRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
  private String name;
  private String email;
  private String phoneNumber;
  private UserRole role;

  public UserResponse(User user) {
    this.name = user.getName();
    this.email = user.getEmail();
    this.phoneNumber = user.getPhoneNumber();
    this.role = user.getRole();
  }
}
