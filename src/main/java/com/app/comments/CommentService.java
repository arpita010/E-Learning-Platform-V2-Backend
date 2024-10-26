package com.app.comments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepo commentRepo;

  public Comment save(Comment comment) {
    return commentRepo.save(comment);
  }

  public List<Comment> fetchAllByPostId(Long postId) throws Exception {
    Optional<List<Comment>> opt = commentRepo.findAllByPostId(postId);
    return opt.orElse(new ArrayList<>());
  }

  public void deleteById(Long commentId) throws Exception {
    Optional<Comment> opt = commentRepo.findById(commentId);
    if (opt.isEmpty()) throw new Exception("Invalid Comment ID");
    commentRepo.delete(opt.get());
  }

  public void deleteAllByPostId(Long postId) throws Exception {
    List<Comment> commentList = fetchAllByPostId(postId);
    commentRepo.deleteAll(commentList);
  }

  public Comment getById(Long commentId) throws Exception {
    Optional<Comment> opt = commentRepo.findById(commentId);
    if (opt.isEmpty()) throw new Exception("Invalid Comment ID");
    return opt.get();
  }
}
