package com.app.otp;

import com.app.enums.OtpType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpLogService {
  private final OtpLogRepo otpLogRepo;

  public void save(
      String otp, String metadata1, String metadata2, OtpType otpType, Integer afterMins) {
    OtpLog otpLog = new OtpLog();
    otpLog.setOtp(otp);
    otpLog.setOtpType(otpType);
    otpLog.setMetadata1(metadata1);
    otpLog.setMetadata2(metadata2);
    otpLog.setOtpExpiryTime(getOtpExpiryTime(afterMins));
    otpLogRepo.save(otpLog);
  }

  private Date getOtpExpiryTime(Integer afterMins) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MINUTE, afterMins);
    return calendar.getTime();
  }

  public OtpLog findLatestByOtpType(OtpType otpType, String metadata2) throws Exception {
    Optional<OtpLog> opt =
        otpLogRepo.findTopByOtpTypeAndMetadata2OrderByCreatedAtDesc(otpType, metadata2);
    return opt.orElse(null);
  }
}
