package com.app.template;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Service
public class TemplateService {
  public String getPopulatedMessage(TemplateType templateType, Map<String, String> dataMap) {
    String templateFileName = templateType.fileName();
    String folder = templateType.folder();
    String content = "";
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      InputStream inputStream = classLoader.getResourceAsStream(folder + "/" + templateFileName);
      content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
      StringSubstitutor substitutor = new StringSubstitutor(dataMap);
      content = substitutor.replace(content);
    } catch (Exception e) {
      log.error(
          "Error while populating message for template {} : {}", templateType, e.getMessage());
    }
    return content;
  }
}
