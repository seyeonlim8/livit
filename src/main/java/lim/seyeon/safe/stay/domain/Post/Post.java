package lim.seyeon.safe.stay.domain.Post;

import jakarta.persistence.*;
import lim.seyeon.safe.stay.domain.Category.Category;
import lim.seyeon.safe.stay.domain.Comment.Comment;
import lim.seyeon.safe.stay.domain.Photo.Photo;
import lim.seyeon.safe.stay.domain.User.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Photo> photos = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public Post() {}

    public Post(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, User user, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
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

    public User getUser() {
        return user;
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

    public void setUser(User user) {
        this.user = user;
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
}
