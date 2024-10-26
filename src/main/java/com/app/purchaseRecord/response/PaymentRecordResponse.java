package com.app.purchaseRecord.response;

import com.app.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PaymentRecordResponse {
  private Long paymentId;
  private PaymentStatus paymentStatus;
  private String userEmail;
  private String courseName;
  private Date placedAt;
}
