package com.app.purchaseRecord.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaymentRecordListResponse {
  private Integer pageNo;
  private Integer pageSize;
  private Long totalRecords;
  private Integer totalPages;
  private Boolean isLast;
  private List<PaymentRecordResponse> paymentRecords;
}
