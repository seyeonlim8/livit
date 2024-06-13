package lim.seyeon.safe.stay.domain;

import java.util.List;

public interface ReviewRepository {
    Review add(Review review);
    Review findReviewById(Long id);
    List<Review> findAll();
    List<Review> findReviewsByUserId(Long user_id);
    List<Review> findReviewsByHouseId(Long house_id);
    Review update(Review review);
    void delete(Long id);
}
