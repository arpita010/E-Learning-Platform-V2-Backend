package com.app.comments.response;

import com.app.comments.Comment;
import com.app.user.User;
import com.app.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
  private Long commentId;
  private String username;
  private String userEmail;
  private Long postId;
  private String comment;

  public CommentResponse(Comment comment, User user) {
    this.commentId = comment.getId();
    this.username = user.getName();
    this.userEmail = user.getEmail();
    this.postId = comment.getPostId();
    this.comment = comment.getComment();
  }
}
