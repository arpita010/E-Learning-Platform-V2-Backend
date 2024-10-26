package com.app.classroom.response;

import com.app.classroom.Classroom;
import com.app.commons.SuperResponse;
import com.app.user.User;
import com.app.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomResponse extends SuperResponse {
  private Long classroomId;
  private String name;
  private String section;
  private List<UserResponse> studentsList;
  private List<UserResponse> instructorsList;

  public ClassroomResponse(Classroom classroom) {
    this.classroomId = classroom.getId();
    this.name = classroom.getName();
    this.section = classroom.getSection();
    this.studentsList =
        null != classroom.getStudents()
            ? classroom.getStudents().stream().map(UserResponse::new).toList()
            : null;
    this.instructorsList =
        null != classroom.getInstructors()
            ? classroom.getInstructors().stream().map(UserResponse::new).toList()
            : null;
  }
}
