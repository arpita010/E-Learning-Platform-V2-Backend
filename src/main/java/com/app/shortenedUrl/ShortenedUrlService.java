package com.app.shortenedUrl;

import com.app.shortenedUrl.response.OriginalUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShortenedUrlService {
  private final ShortenedUrlRepo shortenedUrlRepo;
  private final ShortenedUrlUtil shortenedUrlUtil;

  public OriginalUrlResponse getShortenedUrl(String shortenedValue) throws Exception {
    Optional<ShortenedUrl> opt = shortenedUrlRepo.findByShortenedValue(shortenedValue);
    if (opt.isEmpty())
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid Shortened Key");
    return new OriginalUrlResponse(opt.get());
  }

  public ShortenedUrl create(String originalUrl) throws Exception {
    String shortenedKey = shortenedUrlUtil.getUniqueShortenedKey();
    ShortenedUrl shortenedUrl = new ShortenedUrl();
    shortenedUrl.setShortenedValue(shortenedKey);
    shortenedUrl.setOriginalUrl(originalUrl);
    ShortenedUrl saved = shortenedUrlRepo.save(shortenedUrl);
    return saved;
  }
}
