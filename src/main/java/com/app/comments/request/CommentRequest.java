package com.app.comments.request;

import com.app.comments.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
  private String comment;
  private Long postId;

  public Comment toComment() {
    return Comment.builder().comment(this.comment).postId(this.postId).build();
  }
}
