package com.app.filter;

import com.app.authToken.AuthToken;
import com.app.enums.UserRole;
import com.app.exceptions.AuthException;
import com.app.policy.Access;
import com.app.policy.AuthPolicy;
import com.app.policy.Resource;
import com.app.policy.enums.ApiMethodType;
import com.app.user.User;
import com.app.user.UserService;
import com.app.utils.Decrypter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthFilterService {
  private AuthPolicy authPolicy;
  private final UserService userService;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @PostConstruct
  private void constructAuthPolicy() {
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      InputStream inputStream = classLoader.getResourceAsStream("policy/authPolicyRule.json");
      String jsonValue = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
      authPolicy = objectMapper.readValue(jsonValue, AuthPolicy.class);
      inputStream.close();
    } catch (Exception e) {
      log.error("Error while reading auth policy file : {}", e.getMessage());
    }
  }

  public String[] parseAuthToken(String token) {
    String decodedToken = Decrypter.decrypt(token);
    String[] parts = decodedToken.split("\\|");
    return parts;
  }

  public void doRoleBasedAuthentication(String url, String method, String userRole)
      throws Exception {
    UserRole role = UserRole.valueOf(userRole);
    getResourceBasedOnUrl(url, role, ApiMethodType.valueOf(method));
  }

  private Resource getResourceBasedOnUrl(String url, UserRole role, ApiMethodType method)
      throws Exception {
    Access access = getUserTypeSpecificAccess(role);
    Optional<Resource> opt =
        access.getResources().stream()
            .filter(
                resource ->
                    url.matches(resource.getEndpoint()) && resource.getMethod().equals(method))
            .findFirst();
    if (opt.isEmpty()) throw new AuthException("Invalid Access Request");
    return opt.get();
  }

  private Access getUserTypeSpecificAccess(UserRole role) throws Exception {
    Optional<Access> opt =
        authPolicy.getAccesses().stream()
            .filter(access -> access.getRole().equals(role))
            .findFirst();
    if (opt.isEmpty()) throw new AuthException("Invalid User Role");
    return opt.get();
  }

  public void validateToken(String[] parts, String token) throws Exception {
    String email = parts[1];
    User authUser = userService.findByEmail(email);
    List<AuthToken> authTokenList = authUser.getAuthTokenList();
    Optional<AuthToken> opt =
        authTokenList.stream()
            .filter(authToken -> authToken.getAuthToken().equals(token))
            .findFirst();
    if (opt.isEmpty()) throw new AuthException("Invalid Auth Token");
    log.info("Auth Token : {}", opt.get().getAuthToken());
    MDC.put("email", email);
    MDC.put("authToken", token);
    MDC.put("userId", parts[0]);
  }
}
