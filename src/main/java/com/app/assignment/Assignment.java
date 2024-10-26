package com.app.assignment;

import com.app.assignment.enums.AssignmentStatus;
import com.app.assignment.enums.AssignmentType;
import com.app.commons.SuperEntity;
import com.app.topic.Topic;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Assignment extends SuperEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "longtext")
  private String title;

  @Column(columnDefinition = "longtext")
  private String instructions;

  @ManyToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(name = "topic_id", nullable = true)
  private Topic topic;

  private Long classroomId;

  private Date dueDate;

  private Double points;

  @Enumerated(EnumType.STRING)
  private AssignmentStatus status;

  @Enumerated(EnumType.STRING)
  private AssignmentType type;

  private String creatorEmail;
}
