package com.app.user;

import com.app.authToken.AuthToken;
import com.app.authToken.AuthTokenService;
import com.app.commons.SuperResponse;
import com.app.enums.OtpType;
import com.app.enums.ResponseStatus;
import com.app.exceptions.UserException;
import com.app.otp.OtpLog;
import com.app.otp.OtpLogService;
import com.app.user.request.OtpValidationRequest;
import com.app.user.request.UserCreateRequest;
import com.app.user.request.UserLoginRequest;
import com.app.user.request.UserUpdateRequest;
import com.app.user.response.UserResponse;
import com.app.user.response.ValidateResponse;
import com.app.utils.AuthUtil;
import com.app.utils.Decrypter;
import com.app.utils.Encrypter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepo userRepo;
  private final OtpLogService otpLogService;
  private final AuthTokenService authTokenService;

  public void signout(String token) throws Exception {
    AuthToken authToken = authTokenService.findByAuthToken(token);
    authTokenService.deleteToken(authToken);
  }

  public SuperResponse signupInit(UserCreateRequest userCreateRequest) {
    User user = userCreateRequest.toUser();
    User saved = userRepo.save(user);
    String otp = AuthUtil.generateOtp();
    otpLogService.save(otp, "Signup Initial OTP", saved.getEmail(), OtpType.SIGNUP_OTP, 10);
    // send OTP email
    return new SuperResponse(ResponseStatus.SUCCESS);
  }

  public SuperResponse signinInit(UserLoginRequest loginRequest) throws Exception {
    Optional<User> opt = userRepo.findByEmail(loginRequest.getEmail());
    if (opt.isEmpty()) throw new UserException("Invalid Email ID");
    validatePassword(loginRequest.getPassword(), opt.get().getPassword());
    // send OTP
    String otp = AuthUtil.generateOtp();
    otpLogService.save(otp, "Signin OTP", loginRequest.getEmail(), OtpType.SIGNIN_OTP, 10);
    return new SuperResponse(ResponseStatus.SUCCESS);
  }

  private void validatePassword(String passwordRequested, String originalPassword)
      throws Exception {
    String decodedPassword = Decrypter.decrypt(originalPassword);
    if (!decodedPassword.equals(passwordRequested))
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Incorrect Password");
  }

  public ValidateResponse validateSignupOtp(OtpValidationRequest request) throws Exception {
    Optional<User> userOptional = userRepo.findByEmail(request.getEmail());
    if (userOptional.isEmpty())
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid Email ID");
    OtpLog otpLog = otpLogService.findLatestByOtpType(OtpType.SIGNUP_OTP, request.getEmail());
    if (null == otpLog)
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid Request");
    if (!otpLog.getOtp().equals(request.getOtp())) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Incorrect OTP");
    }
    AuthToken authToken = authTokenService.create(userOptional.get());
    return new ValidateResponse(
        request.getEmail(), authToken.getAuthToken(), userOptional.get().getRole());
  }

  public ValidateResponse validateSigninOtp(OtpValidationRequest request) throws Exception {
    Optional<User> userOptional = userRepo.findByEmail(request.getEmail());
    if (userOptional.isEmpty())
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid Email ID");
    OtpLog otpLog = otpLogService.findLatestByOtpType(OtpType.SIGNIN_OTP, request.getEmail());
    if (null == otpLog)
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid Request");
    if (!otpLog.getOtp().equals(request.getOtp())) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Incorrect OTP");
    }
    AuthToken authToken = authTokenService.create(userOptional.get());
    return new ValidateResponse(
        request.getEmail(), authToken.getAuthToken(), userOptional.get().getRole());
  }

  public void updateUserDetails(UserUpdateRequest request) throws Exception {
    Optional<User> userOptional = userRepo.findByEmail(request.getEmail());
    if (userOptional.isEmpty()) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid Email ID");
    }
    User user = userOptional.get();
    if (request.getName() != null) user.setName(request.getName());
    if (request.getPassword() != null) {
      user.setPassword(Encrypter.encrypt(request.getPassword()));
    }
    if (request.getPhoneNumber() != null) {
      user.setPhoneNumber(request.getPhoneNumber());
    }
    userRepo.save(user);
  }

  public User findByEmail(String email) throws Exception {
    Optional<User> opt = userRepo.findByEmail(email);
    if (opt.isEmpty()) throw new UserException("User not found");
    return opt.get();
  }

  public User getById(Long userId) throws Exception {
    Optional<User> user = userRepo.findById(userId);
    if (user.isEmpty()) throw new UserException("Invalid User ID");
    return user.get();
  }
}
