package lim.seyeon.safe.stay.domain.Photo;

import jakarta.persistence.*;
import lim.seyeon.safe.stay.domain.Post.Post;

@Entity
@Table(name = "photos")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Photo() {}

    public Photo(Long id, String url, Post post) {
        this.id = id;
        this.url = url;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Post getPost() {
        return post;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
