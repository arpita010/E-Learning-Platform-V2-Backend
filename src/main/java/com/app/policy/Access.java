package com.app.policy;

import com.app.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Access {
  private UserRole role;
  private List<Resource> resources;
}
