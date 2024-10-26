package com.app.shortenedUrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;

@Component
public class ShortenedUrlUtil {
  private char[] characters =
      "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_".toCharArray();
  private static int maxKeySize = 4;

  private static final int startValue = 0;
  private static final int endValue = 63;

  private ShortenedUrlRepo shortenedUrlRepo;

  @Autowired
  public ShortenedUrlUtil(ShortenedUrlRepo shortenedUrlRepo) {
    this.shortenedUrlRepo = shortenedUrlRepo;
  }

  public String getUniqueShortenedKey() {
    int i = 0;
    StringBuilder res = new StringBuilder();
    while (i < maxKeySize) {
      Random random = new Random();
      int idx = random.nextInt(endValue - startValue) + startValue;
      res.append(characters[idx]);
      i++;
    }
    Optional<ShortenedUrl> opt = shortenedUrlRepo.findByShortenedValue(res.toString());
    if (opt.isPresent()) {
      return getUniqueShortenedKey();
    }
    return res.toString();
  }

}
