package com.app.policy;

import com.app.policy.enums.ResourceName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthPolicy {
  private List<ResourceName> openAccess;
  private List<Access> accesses;
  private List<Resource> resources;
}
