package com.app.exceptions;

import com.app.commons.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@ResponseBody
@RestControllerAdvice
public class ExceptionAdviceMaster {

  @ExceptionHandler({Exception.class})
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponse handleException(Exception e) {
    log.error("[Error] Exception : {}", e.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
    return errorResponse;
  }
}
