package com.app.otp;

import com.app.enums.OtpType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpLogRepo extends CrudRepository<OtpLog, Long> {
  Optional<OtpLog> findTopByOtpTypeAndMetadata2OrderByCreatedAtDesc(
      OtpType otpType, String metadata2);
}
