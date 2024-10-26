package com.app.classroom;

import com.app.commons.SuperEntity;
import com.app.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

// @Data
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Classroom extends SuperEntity {
  // classroom code need to be patched.
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String section;

  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.MERGE, CascadeType.REFRESH})
  @JsonIgnore
  private User creator;

  @JoinTable(
      name = "classroom_instructor",
      joinColumns = @JoinColumn(name = "classroom_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  @ManyToMany(
      cascade = {CascadeType.MERGE, CascadeType.REFRESH},
      fetch = FetchType.LAZY)
  @JsonManagedReference
  @JsonIgnore
  private List<User> instructors;

  @JsonManagedReference
  @ManyToMany(
      cascade = {CascadeType.MERGE, CascadeType.REFRESH},
      fetch = FetchType.LAZY)
  @JoinTable(
      name = "classroom_students",
      joinColumns = @JoinColumn(name = "clasroom_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
  @JsonIgnore
  private List<User> students;

  public void addStudent(User student) {
    //
    //    this.students.add(student);
    //    student.getStudentClassroomsList().add(this);
    if (!this.getStudents().contains(student)) {
      this.students.add(student);
    }
    if (!student.getStudentClassroomsList().contains(this)) {
      student.getStudentClassroomsList().add(this);
    }
  }

  public void addInstructor(User instructor) {
    if (!this.getInstructors().contains(instructor)) {
      this.instructors.add(instructor);
    }
    if (!instructor.getInstructorClassroomsList().contains(this)) {
      instructor.getInstructorClassroomsList().add(this);
    }
  }
}
