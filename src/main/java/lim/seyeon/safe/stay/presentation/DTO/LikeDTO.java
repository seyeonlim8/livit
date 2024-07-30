package lim.seyeon.safe.stay.presentation.DTO;

import lim.seyeon.safe.stay.domain.Like.Like;
import lim.seyeon.safe.stay.domain.Post.Post;
import lim.seyeon.safe.stay.domain.User.User;

import java.time.LocalDateTime;

public class LikeDTO {
    private Long id;
    private Long userId;
    private Long postId;

    public LikeDTO() {}

    public LikeDTO(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }

    public LikeDTO(Long id, Long userId, Long postId) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
    }

    public Long getId() {
        return id;
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

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public static Like toEntity(LikeDTO likeDTO, User user, Post post) {
        Like like = new Like(
                likeDTO.getId(),
                user,
                post
        );
        return like;
    }

    public static LikeDTO toDTO(Like like) {
        LikeDTO likeDTO = new LikeDTO(
                like.getId(),
                like.getUser().getId(),
                like.getPost().getId()
        );
        return likeDTO;
    }
}
