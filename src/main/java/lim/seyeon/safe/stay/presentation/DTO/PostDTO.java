package lim.seyeon.safe.stay.presentation.DTO;

import jakarta.validation.constraints.NotNull;
import lim.seyeon.safe.stay.domain.Category.Category;
import lim.seyeon.safe.stay.domain.Comment.Comment;
import lim.seyeon.safe.stay.domain.Photo.Photo;
import lim.seyeon.safe.stay.domain.Post.Post;
import lim.seyeon.safe.stay.domain.User.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostDTO {
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private Category category;
    private List<Photo> photos = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();

    public PostDTO() {}

    public PostDTO(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public Category getCategory() {
        return category;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static Post toEntity(PostDTO postDTO, User user) {
        Post post = new Post (
                postDTO.getId(),
                postDTO.getTitle(),
                postDTO.getContent(),
                postDTO.getCreatedAt(),
                postDTO.getUpdatedAt(),
                user,
                postDTO.getCategory()
        );
        return post;
    }

    public static PostDTO toDTO(Post post) {
        PostDTO postDTO = new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getUser().getId(),
                post.getCategory()
        );
        return postDTO;
    }
}
