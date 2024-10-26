package com.app.commons;

import com.app.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse extends SuperResponse {
  private String message;

  public ErrorResponse(String message) {
    super(ResponseStatus.FAILED);
    this.message = message;
  }
}
