package com.app.exceptions;

public class AuthException extends RuntimeException {
  public AuthException(String message) {
    super(message);
  }
}
