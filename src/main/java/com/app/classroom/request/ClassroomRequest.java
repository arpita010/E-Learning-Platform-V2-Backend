package com.app.classroom.request;

import com.app.classroom.Classroom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomRequest {
  private String name;
  private String section;

  public Classroom toClassroom() {
    return Classroom.builder().name(this.name).section(this.section).build();
  }

  public void updateDetails(Classroom classroom) {
    Optional.ofNullable(this.name).ifPresent(classroom::setName);
    Optional.ofNullable(this.section).ifPresent(classroom::setSection);
  }
}
