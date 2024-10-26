package com.app.shortenedUrl;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortenedUrlRepo extends CrudRepository<ShortenedUrl, Long> {
  Optional<ShortenedUrl> findByShortenedValue(String shortenedValue);
}
