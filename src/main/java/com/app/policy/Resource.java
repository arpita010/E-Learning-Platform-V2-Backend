package com.app.policy;

import com.app.policy.enums.ApiMethodType;
import com.app.policy.enums.ResourceName;
import com.app.policy.enums.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
  private ResourceName name;
  private String endpoint;
  private ApiMethodType method;
  private ResourceType type;
}
