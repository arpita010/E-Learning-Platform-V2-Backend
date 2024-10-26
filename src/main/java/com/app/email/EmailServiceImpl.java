package com.app.email;

import com.app.email.request.EmailSendRequest;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  @Value("${sender.mail}")
  private String senderEmail;

  private final JavaMailSender mailSender;

  @Override
  public void sendEmail(EmailSendRequest emailSendRequest) {
    try {
      boolean multipart =
          null != emailSendRequest.getFiles() && !emailSendRequest.getFiles().isEmpty();
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, multipart);
      helper.setTo(convertListToArray(emailSendRequest.getToList()));
      helper.setCc(convertListToArray(emailSendRequest.getCcList()));
      helper.setBcc(convertListToArray(emailSendRequest.getBccList()));
      helper.setSubject(emailSendRequest.getSubject());
      helper.setText(emailSendRequest.getContent(), true);
      helper.setReplyTo(senderEmail);
      addAttachments(emailSendRequest, helper);
      mailSender.send(mimeMessage);
    } catch (Exception e) {
      log.error("Error while sending Email : {}", e.getMessage());
    }
  }

  private void addAttachments(EmailSendRequest emailSendRequest, MimeMessageHelper helper) {
    if (null == emailSendRequest || null == emailSendRequest.getFiles()) return;
    try {
      for (File file : emailSendRequest.getFiles()) {
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        helper.addAttachment(fileSystemResource.getFilename(), file);
      }
    } catch (Exception e) {
      log.error("Error while attaching files to Email : {}", e.getMessage());
    }
  }

  private String[] convertListToArray(List<String> emailList) {
    if (emailList == null || emailList.isEmpty()) {
      return new String[0];
    }
    String[] arr = new String[emailList.size()];
    int i = 0;
    for (String email : emailList) {
      arr[i++] = email;
    }
    log.info("Email List : {}", Arrays.toString(arr));
    return arr;
  }
}
