package com.app.payment.response;

import com.app.commons.SuperResponse;
import com.app.enums.PaymentMode;
import com.app.enums.PaymentStatus;
import com.app.payment.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse extends SuperResponse {
  private Long paymentId;
  private String userEmail;
  private PaymentMode paymentMode;
  private Double amount;
  private PaymentStatus paymentStatus;

  public PaymentResponse(Payment payment) {
    this.paymentId = payment.getId();
    this.userEmail = payment.getUserEmail();
    this.paymentMode = payment.getPaymentMode();
    this.amount = payment.getAmount();
    this.paymentStatus = payment.getPaymentStatus();
  }
}
