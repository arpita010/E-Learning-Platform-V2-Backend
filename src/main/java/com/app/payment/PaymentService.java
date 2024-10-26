package com.app.payment;

import com.app.enums.OtpType;
import com.app.enums.PaymentStatus;
import com.app.otp.OtpLog;
import com.app.otp.OtpLogService;
import com.app.payment.request.PaymentRequest;
import com.app.payment.response.PaymentResponse;
import com.app.utils.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
  private final PaymentRepo paymentRepo;
  private final OtpLogService otpLogService;

  public PaymentResponse create(PaymentRequest request) {
    Payment payment = request.toPayment();
    Payment saved = paymentRepo.save(payment);
    sendOtpToEmail(saved);
    return new PaymentResponse(saved);
  }

  private void sendOtpToEmail(Payment payment) {
    String otp = AuthUtil.generateOtp();
    otpLogService.save(
        otp,
        "Payment Processing OTP",
        String.valueOf(payment.getId()),
        OtpType.PAYMENT_OTP,
        15);
    // send email with otp patch
  }

  public PaymentResponse validatePaymentOTP(Long paymentId, String otp) throws Exception {
    Optional<Payment> opt = paymentRepo.findById(paymentId);
    if (opt.isEmpty()) throw new Exception("Invalid Payment ID");
    Payment payment = opt.get();
    OtpLog otpLog =
        otpLogService.findLatestByOtpType(
            OtpType.PAYMENT_OTP, String.valueOf(payment.getId()));
    if (null == otpLog || !otpLog.getOtp().equals(otp)) {
      throw new Exception("Invalid OTP");
    }
    payment.setPaymentStatus(PaymentStatus.SUCCESS);
    Payment saved = paymentRepo.save(payment);
    return new PaymentResponse(saved);
  }

  public Payment getByID(Long paymentId) throws Exception {
    Optional<Payment> opt = paymentRepo.findById(paymentId);
    if (opt.isEmpty())
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid Payment ID.");
    return opt.get();
  }

}
