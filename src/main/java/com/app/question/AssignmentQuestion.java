package com.app.question;

import com.app.commons.SuperEntity;
import com.app.question.enums.QuestionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentQuestion extends SuperEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "longtext")
  private String question;

  @Enumerated(EnumType.STRING)
  private QuestionType type;

  private Boolean isRequired;

  private Long assignmentId;
}
