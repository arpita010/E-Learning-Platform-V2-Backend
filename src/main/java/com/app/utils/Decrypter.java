package com.app.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Decrypter {
  public static String decrypt(String value) {
    byte[] decodedBytes = Base64.getDecoder().decode(value);
    return new String(decodedBytes);
  }
}
