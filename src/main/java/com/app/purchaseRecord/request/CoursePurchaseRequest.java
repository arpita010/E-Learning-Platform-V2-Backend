package com.app.purchaseRecord.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePurchaseRequest {
  private String email;
  private Long courseId;
  private Long paymentId;
}
