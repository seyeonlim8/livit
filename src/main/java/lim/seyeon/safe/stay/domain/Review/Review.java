package lim.seyeon.safe.stay.domain.Review;

import jakarta.persistence.*;
import lim.seyeon.safe.stay.domain.House.House;
import lim.seyeon.safe.stay.domain.User.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "houseid", nullable = false)
    private House house;

    @Column(name = "stars", nullable = false)
    private int stars;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "createdat", nullable = false)
    private LocalDateTime createdat;

    public Review() {}

    public Review(User user, House house, int stars, String title, String content, LocalDateTime createdat) {
        this.user = user;
        this.house = house;
        this.stars = stars;
        this.title = title;
        this.content = content;
        this.createdat = createdat;
    }

    public Review(Long id, User user, House house, int stars, String title, String content, LocalDateTime createdat) {
        this.id = id;
        this.user = user;
        this.house = house;
        this.stars = stars;
        this.title = title;
        this.content = content;
        this.createdat = createdat;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public House getHouse() {
        return house;
    }

    public int getStars() {
        return stars;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedat() {
        return createdat;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedat(LocalDateTime createdat) {
        this.createdat = createdat;
    }
}
