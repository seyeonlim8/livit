package lim.seyeon.safe.stay.presentation.DTO;

import lim.seyeon.safe.stay.domain.Like.Like;
import lim.seyeon.safe.stay.domain.Post.Post;
import lim.seyeon.safe.stay.domain.User.User;

import java.time.LocalDateTime;

public class LikeDTO {
    private Long id;
    private User user;
    private Post post;
    private LocalDateTime likedAt;
    public LikeDTO() {}

    public LikeDTO(Long id, User user, Post post, LocalDateTime likedAt) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.likedAt = likedAt;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public LocalDateTime getLikedAt() {
        return likedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setLikedAt(LocalDateTime likedAt) {
        this.likedAt = likedAt;
    }

    public static Like toEntity(LikeDTO likeDTO) {
        Like like = new Like(
                likeDTO.getId(),
                likeDTO.getUser(),
                likeDTO.getPost(),
                likeDTO.getLikedAt()
        );
        return like;
    }

    public static LikeDTO toDTO(Like like) {
        LikeDTO likeDTO = new LikeDTO(
                like.getId(),
                like.getUser(),
                like.getPost(),
                like.getLikedAt()
        );
        return likeDTO;
    }
}
