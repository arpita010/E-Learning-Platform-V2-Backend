package com.app.course.response;

import com.app.commons.SuperResponse;
import com.app.course.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse extends SuperResponse {
  private Long courseId;
  private String name;
  private String description;
  private Double originalPrice;
  private Double sellingPrice;
  private Long courseDurationInHours;
  private String thumbnailUrl;
  private String instructor;
  private Long totalChapters;
  private Boolean isPurchased;

  public CourseResponse(Course course) {
    this.courseId = course.getId();
    this.name = course.getName();
    this.description = course.getDescription();
    this.originalPrice = course.getOriginalPrice();
    this.sellingPrice = course.getSellingPrice();
    this.courseDurationInHours = course.getCourseDurationInHours();
    this.thumbnailUrl = course.getThumbnailUrl();
    this.instructor = course.getInstructor().getName();
  }
}
