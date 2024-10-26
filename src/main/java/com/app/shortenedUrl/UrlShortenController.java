package com.app.shortenedUrl;

import com.app.shortenedUrl.response.OriginalUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/shortened")
@RequiredArgsConstructor
public class UrlShortenController {
  private final ShortenedUrlService shortenedUrlService;

  @GetMapping("/{shortenedKey}/fetch/original")
  public OriginalUrlResponse getOriginalUrl(@PathVariable String shortenedKey) throws Exception {
    return shortenedUrlService.getShortenedUrl(shortenedKey);
  }
}
