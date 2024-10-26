package com.app.shortenedUrl.response;

import com.app.commons.SuperResponse;
import com.app.shortenedUrl.ShortenedUrl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OriginalUrlResponse extends SuperResponse {
  private String originalUrl;

  public OriginalUrlResponse(ShortenedUrl url) {
    this.originalUrl = url.getOriginalUrl();
  }
}
