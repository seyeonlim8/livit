package lim.seyeon.safe.stay.presentation.DTO;

import jakarta.validation.constraints.NotNull;
import lim.seyeon.safe.stay.domain.Comment.Comment;
import lim.seyeon.safe.stay.domain.Post.Post;
import lim.seyeon.safe.stay.domain.User.User;

import java.time.LocalDateTime;

public class CommentDTO {
    private Long id;
    @NotNull
    private String content;
    private LocalDateTime createdAt;
    private Long userId;
    private Long postId;

    public CommentDTO() {}

    public CommentDTO(Long id, String content, LocalDateTime createdAt, Long userId, Long postId) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
        this.postId = postId;
    }

    public Long getId() {
        return id;
    }

    public @NotNull String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public static Comment toEntity(CommentDTO commentDTO, User user, Post post) {
        Comment comment = new Comment(
                commentDTO.getId(),
                commentDTO.getContent(),
                commentDTO.getCreatedAt(),
                user,
                post
        );
        return comment;
    }

    public static CommentDTO toDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUser().getId(),
                comment.getPost().getId()
        );
        return commentDTO;
    }
}
