package com.app.authToken;

import com.app.commons.SuperResponse;
import com.app.enums.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {
  private final AuthTokenService authTokenService;

  @GetMapping("/validate")
  public SuperResponse validateToken() throws Exception {
    authTokenService.validateAuthToken(MDC.get("authToken"));
    return new SuperResponse(ResponseStatus.SUCCESS);
  }
}
