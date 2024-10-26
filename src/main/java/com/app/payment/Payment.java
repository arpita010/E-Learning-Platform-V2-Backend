package com.app.payment;

import com.app.commons.SuperEntity;
import com.app.enums.PaymentMode;
import com.app.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends SuperEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String userEmail;

  @Enumerated(EnumType.STRING)
  private PaymentMode paymentMode;

  private Double amount;

  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;
}
