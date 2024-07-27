package lim.seyeon.safe.stay.domain.Comment;

import java.util.List;

public interface CommentRepository {
    Comment add(Comment comment);
    Comment findCommentById(Long id);
    List<Comment> findCommentsByUserId(Long userId);
    List<Comment> findCommentsByPostId(Long postId);
    List<Comment> findAllComments();
    Comment update(Comment comment);
    void delete(Long id);
}
