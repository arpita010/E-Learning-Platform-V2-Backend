package com.app.user.request;

import com.app.enums.UserRole;
import com.app.user.User;
import com.app.utils.Encrypter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
  private String name;
  private String email;
  private String password;
  private String phoneNumber;
  private UserRole role;

  public User toUser() {
    return User.builder()
        .name(this.name)
        .email(this.email)
        .password(Encrypter.encrypt(this.password))
        .phoneNumber(this.phoneNumber)
        .role(this.role)
        .build();
  }
}
