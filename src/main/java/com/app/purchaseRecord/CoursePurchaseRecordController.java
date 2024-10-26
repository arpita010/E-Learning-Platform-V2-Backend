package com.app.purchaseRecord;

import com.app.commons.SuperResponse;
import com.app.course.response.CourseListResponse;
import com.app.enums.ResponseStatus;
import com.app.purchaseRecord.request.CoursePurchaseRequest;
import com.app.purchaseRecord.response.PaymentRecordListResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class CoursePurchaseRecordController {
  private final CoursePurchaseRecordService coursePurchaseRecordService;

  @PostMapping("/course/purchase")
  public SuperResponse purchase(@RequestBody CoursePurchaseRequest request) throws Exception {
    coursePurchaseRecordService.purchaseCourse(request);
    return new SuperResponse(ResponseStatus.SUCCESS);
  }

  @GetMapping("/course/purchased/fetchAll")
  public CourseListResponse getAllPurchasedCourses(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "") String queryName)
      throws Exception {
    String email = MDC.get("email");
    return coursePurchaseRecordService.getAllPurchasedCourses(email, queryName, pageNo, pageSize);
  }

  @GetMapping("/payment/records/fetchAll")
  public PaymentRecordListResponse getAllPaymentRecords(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize)
      throws Exception {
    return coursePurchaseRecordService.fetchAllPaymentRecords(pageNo, pageSize);
  }

  @GetMapping("/payment/records/download")
  public ResponseEntity<Resource> downloadAllPaymentRecordsFileAsCsv() throws Exception {
    File file = coursePurchaseRecordService.downloadAllPaymentRecordsFile();
    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
    if (file != null) {
      file.delete();
    }
    return ResponseEntity.ok()
        .contentLength(file.length())
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(resource);
  }
}
