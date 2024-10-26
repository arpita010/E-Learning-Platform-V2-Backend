package com.app.utils;

import com.app.user.User;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class AuthUtil {
  private static final Integer MIN_LIMIT = 10000;
  private static final Integer MAX_LIMIT = 99999;

  public static String generateOtp() {
    Random random = new Random();
//    Integer value = random.nextInt(MAX_LIMIT - MIN_LIMIT + 1) + MIN_LIMIT;

//    return String.valueOf(value);
    return "00000";
  }

  // userId|userEmail|userRole|randomPart
  public static String generateAuthToken(User user) {
    String value =
        user.getId()
            + "|"
            + user.getEmail()
            + "|"
            + String.valueOf(user.getRole())
            + "|"
            + generateRandomString();
    return Encrypter.encrypt(value);
  }

  private static String generateRandomString() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }
}
