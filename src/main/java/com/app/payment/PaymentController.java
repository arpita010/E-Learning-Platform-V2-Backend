package com.app.payment;

import com.app.commons.SuperResponse;
import com.app.enums.ResponseStatus;
import com.app.payment.request.PaymentRequest;
import com.app.payment.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
  private final PaymentService paymentService;

  @PostMapping("/create")
  public PaymentResponse create(@RequestBody PaymentRequest request) throws Exception {
    return paymentService.create(request);
  }

  @GetMapping("/{paymentId}/otp/{otp}/validate")
  public PaymentResponse validatePaymentOtp(@PathVariable Long paymentId, @PathVariable String otp)
      throws Exception {
    return paymentService.validatePaymentOTP(paymentId, otp);
  }

}
