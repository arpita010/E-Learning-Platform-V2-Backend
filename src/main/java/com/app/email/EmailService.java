package com.app.email;

import com.app.email.request.EmailSendRequest;

public interface EmailService {
  void sendEmail(EmailSendRequest emailSendRequest);
}
