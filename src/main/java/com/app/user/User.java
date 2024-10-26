package com.app.user;

import com.app.authToken.AuthToken;
import com.app.classroom.Classroom;
import com.app.commons.SuperEntity;
import com.app.course.Course;
import com.app.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class User extends SuperEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String email;
  private String phoneNumber;

  @Column(columnDefinition = "longtext")
  private String password;

  @Enumerated(EnumType.STRING)
  private UserRole role;

  @JsonIgnore
  @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "user", fetch = FetchType.EAGER)
  @JsonManagedReference(value = "auth-token-user")
  private List<AuthToken> authTokenList;

  @ManyToMany
  @JoinTable(
      name = "student_course_enrolled_in",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
  @JsonIgnore
  private List<Course> courseList;

  @JsonBackReference
  @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
  //  @JoinTable(
  //      name = "classroom_instructor",
  //      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
  //      inverseJoinColumns = @JoinColumn(name = "classroom_id", referencedColumnName = "id"))
  @JsonIgnore
  private List<Classroom> studentClassroomsList;

  @JsonBackReference
  @ManyToMany(mappedBy = "instructors", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Classroom> instructorClassroomsList;
}
