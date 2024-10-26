package com.app.otp;

import com.app.commons.SuperEntity;
import com.app.enums.OtpType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpLog extends SuperEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String otp;

  private String metadata1;
  private String metadata2;

  @Enumerated(EnumType.STRING)
  private OtpType otpType;

  private Date otpExpiryTime;
}
