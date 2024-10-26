package com.app.commons;

import com.app.enums.ResponseStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SuperResponse {
  private ResponseStatus status;

  public SuperResponse() {
    this.status = ResponseStatus.SUCCESS;
  }

  public SuperResponse(ResponseStatus status) {
    this.status = status;
  }
}
