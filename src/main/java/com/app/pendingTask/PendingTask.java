package com.app.pendingTask;

import com.app.commons.SuperEntity;
import com.app.pendingTask.enums.PendingTaskStatus;
import com.app.pendingTask.enums.PendingTaskType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PendingTask extends SuperEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;

  @Enumerated(EnumType.STRING)
  private PendingTaskType taskType;

  @Enumerated(EnumType.STRING)
  private PendingTaskStatus status;

  private String refereeEmail;
}
