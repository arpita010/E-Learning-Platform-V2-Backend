package com.app.authToken;

import com.app.exceptions.AuthException;
import com.app.user.User;
import com.app.utils.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthTokenService {
  private final AuthTokenRepo authTokenRepo;

  public AuthToken create(User user) {
    AuthToken authToken = new AuthToken();
    authToken.setAuthToken(AuthUtil.generateAuthToken(user));
    authToken.setUser(user);
    return authTokenRepo.save(authToken);
  }

  public void validateAuthToken(String authToken) throws Exception {
    Optional<AuthToken> opt = authTokenRepo.findByAuthToken(authToken);
    if (opt.isEmpty())
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid Auth Token");
    String userId = MDC.get("userId");
    if (opt.get().getUser().getId() != Long.parseLong(userId)) {
      throw new Exception("Invalid Auth Token");
    }
  }

  public AuthToken findByAuthToken(String authToken) throws Exception {
    Optional<AuthToken> opt = authTokenRepo.findByAuthToken(authToken);
    return opt.orElseThrow(() -> new AuthException("Invalid Auth Token"));
  }

  public void deleteToken(AuthToken authToken) {
    authTokenRepo.delete(authToken);
  }
}
