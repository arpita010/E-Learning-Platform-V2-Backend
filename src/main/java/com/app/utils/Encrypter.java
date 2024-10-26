package com.app.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Encrypter {
  public static String encrypt(String value) {
    String encodedString = Base64.getEncoder().encodeToString(value.getBytes());
    return encodedString;
  }
}
