package com.app.chapter;

import com.app.commons.SuperEntity;
import com.app.course.Course;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Chapter extends SuperEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "longtext")
  private String name;

  private Long durationInMins; // in minutes

  @Column(columnDefinition = "longtext")
  private String description;

  @Column(columnDefinition = "longtext")
  private String thumbnailUrl;

  @Column(columnDefinition = "longtext")
  private String videoUrl;

  @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false)
  @JsonBackReference("course-chapter")
  @JsonIgnore
  private Course course;
}
