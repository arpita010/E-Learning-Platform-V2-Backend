package com.app.email.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.File;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailSendRequest {
  private String content;
  private String subject;
  private List<File> files;
  private List<String> ccList;
  private List<String> bccList;
  private List<String> toList;
  private List<String> replyToList;
}
