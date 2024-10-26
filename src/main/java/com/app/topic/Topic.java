package com.app.topic;

import com.app.assignment.Assignment;
import com.app.commons.SuperEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Topic extends SuperEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "longtext")
  private String name;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic")
  @JsonIgnore
  private List<Assignment> assignmentList;

  private Long classroomId;
}
