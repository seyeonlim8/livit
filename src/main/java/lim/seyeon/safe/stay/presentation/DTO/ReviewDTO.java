package lim.seyeon.safe.stay.presentation.DTO;

import jakarta.validation.constraints.NotNull;
import lim.seyeon.safe.stay.domain.House.House;
import lim.seyeon.safe.stay.domain.Review.Review;
import lim.seyeon.safe.stay.domain.User.User;

import java.time.LocalDateTime;

public class ReviewDTO {

    private Long id;

    @NotNull
    private Long userid;

    @NotNull
    private Long houseid;

    @NotNull
    private int stars;

    private String title;

    private String content;

    @NotNull
    private LocalDateTime createdat;

    public ReviewDTO() {}

    public ReviewDTO(Long userid, Long houseid, int stars, String title, String content, LocalDateTime createdat) {
        this.userid = userid;
        this.houseid = houseid;
        this.stars = stars;
        this.title = title;
        this.content = content;
        this.createdat = createdat;
    }

    public ReviewDTO(Long id, Long userid, Long houseid, int stars, String title, String content, LocalDateTime createdat) {
        this.id = id;
        this.userid = userid;
        this.houseid = houseid;
        this.stars = stars;
        this.title = title;
        this.content = content;
        this.createdat = createdat;
    }

    public Long getId() {
        return id;
    }

    public Long getUserid() {
        return userid;
    }

    public Long getHouseid() {
        return houseid;
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

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public void setHouseid(Long houseid) {
        this.houseid = houseid;
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

    public static Review toEntity(ReviewDTO reviewDTO, User user, House house) {
        Review review = new Review(
                reviewDTO.getId(),
                user,
                house,
                reviewDTO.getStars(),
                reviewDTO.getTitle(),
                reviewDTO.getContent(),
                reviewDTO.getCreatedat()
        );
        return review;
    }

    public static ReviewDTO toDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO(
                review.getId(),
                review.getUser().getId(),
                review.getHouse().getId(),
                review.getStars(),
                review.getTitle(),
                review.getContent(),
                review.getCreatedat()
        );
        return reviewDTO;
    }
}
