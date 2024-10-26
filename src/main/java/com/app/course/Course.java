package com.app.course;

import com.app.chapter.Chapter;
import com.app.commons.SuperEntity;
import com.app.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course extends SuperEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "longtext")
  private String name;

  @Column(columnDefinition = "longtext")
  private String description;

  private Double originalPrice;
  private Double sellingPrice;

  private Long courseDurationInHours;

  private Integer subscriptionPlanTimeInMonths;

  @Column(columnDefinition = "longtext")
  private String thumbnailUrl;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(nullable = false)
  @JsonIgnore
  private User instructor;

  @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "course")
  @JsonManagedReference("course-chapter")
  @JsonIgnore
  private List<Chapter> chapterList;

  @JoinTable(
      name = "student_course_enrolled_in",
      joinColumns = @JoinColumn(name = "course_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JsonIgnore
  private List<User> studentsEnrolledInList;

  @Column(columnDefinition = "tinyint(1) default true")
  private boolean isActive = true;
}
