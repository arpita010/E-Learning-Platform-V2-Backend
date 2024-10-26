package com.app.user;

import com.app.commons.SuperResponse;
import com.app.enums.ResponseStatus;
import com.app.user.request.OtpValidationRequest;
import com.app.user.request.UserCreateRequest;
import com.app.user.request.UserLoginRequest;
import com.app.user.request.UserUpdateRequest;
import com.app.user.response.UserResponse;
import com.app.user.response.ValidateResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/signup/init")
  private SuperResponse initializeUserCreation(@RequestBody UserCreateRequest userCreateRequest)
      throws Exception {
    return userService.signupInit(userCreateRequest);
  }

  @PostMapping("/signin/init")
  private SuperResponse initializeUserLogin(@RequestBody UserLoginRequest loginRequest)
      throws Exception {
    return userService.signinInit(loginRequest);
  }

  @PostMapping("/signup/validate")
  private ValidateResponse validateSignupOtp(@RequestBody OtpValidationRequest request)
      throws Exception {
    return userService.validateSignupOtp(request);
  }

  @PostMapping("/signin/validate")
  private ValidateResponse validateSigninOtp(@RequestBody OtpValidationRequest request)
      throws Exception {
    return userService.validateSigninOtp(request);
  }

  @PostMapping("/update")
  private SuperResponse updateUser(@RequestBody UserUpdateRequest request) throws Exception {
    userService.updateUserDetails(request);
    return new SuperResponse(ResponseStatus.SUCCESS);
  }

  @GetMapping("/current/details")
  private UserResponse getCurrentUserDetails() throws Exception {
    String email = MDC.get("email");
    return new UserResponse(userService.findByEmail(email));
  }

  @PostMapping("/signout")
  private SuperResponse signout() throws Exception {
    String authToken = MDC.get("authToken");
    userService.signout(authToken);
    return new SuperResponse(ResponseStatus.SUCCESS);
  }
}
