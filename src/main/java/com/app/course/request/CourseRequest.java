package com.app.course.request;

import com.app.course.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {
  private String name;
  private String description;
  private Boolean isPremium; // need to be patched on frontend
  private Double originalPrice;
  private Double sellingPrice;
  private Long courseDurationInHours; // in hours

  public Course toCourse() {
    Course course =
        Course.builder()
            .name(this.name)
            .description(this.description)
            .originalPrice(this.originalPrice)
            .sellingPrice(this.sellingPrice)
            .courseDurationInHours(this.courseDurationInHours)
            .isActive(true)
            .build();
    return course;
  }

  public void updateDetails(Course course) {
    Optional.ofNullable(this.name).ifPresent(course::setName);
    Optional.ofNullable(this.description).ifPresent(course::setDescription);
    Optional.ofNullable(this.originalPrice).ifPresent(course::setOriginalPrice);
    Optional.ofNullable(this.sellingPrice).ifPresent(course::setSellingPrice);
    Optional.ofNullable(this.courseDurationInHours).ifPresent(course::setCourseDurationInHours);
  }
}
