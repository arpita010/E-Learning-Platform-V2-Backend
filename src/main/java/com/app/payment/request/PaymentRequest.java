package com.app.payment.request;

import com.app.enums.PaymentMode;
import com.app.enums.PaymentStatus;
import com.app.payment.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
  private String userEmail;
  private PaymentMode paymentMode;
  private Double amount;
  private PaymentStatus paymentStatus;

  public Payment toPayment() {
    return Payment.builder()
        .userEmail(this.userEmail)
        .paymentMode(this.paymentMode)
        .amount(this.amount)
        .paymentStatus(this.paymentStatus)
        .build();
  }
}
