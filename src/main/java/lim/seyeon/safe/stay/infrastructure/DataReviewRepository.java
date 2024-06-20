package lim.seyeon.safe.stay.infrastructure;

import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.domain.Review.Review;
import lim.seyeon.safe.stay.domain.Review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataReviewRepository implements ReviewRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DataReviewRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Review add(Review review) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameter = new MapSqlParameterSource()
                .addValue("id", review.getId())
                .addValue("userid", review.getUser().getId())
                .addValue("houseid", review.getHouse().getId())
                .addValue("stars", review.getStars())
                .addValue("title", review.getTitle())
                .addValue("content", review.getContent())
                .addValue("createdat", review.getCreatedat()
                );
        namedParameterJdbcTemplate.update(
                "INSERT INTO reviews (id, userid, houseid, stars, title, content, createdat) VALUES (:id, :userid, :houseid, :stars, :title, :content, :createdat)",
                namedParameter, keyHolder
        );
        Long generatedId = keyHolder.getKey().longValue();
        review.setId(generatedId);
        return review;
    }

    public Review findReviewById(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
        Review review = null;
        try {
            review = namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM reviews WHERE id = :id",
                    namedParameter, new ReviewRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Review with id " + id + " not found");
        }
        return review;
    }

    public List<Review> findAll() {
        List<Review> reviews = namedParameterJdbcTemplate.query(
                "SELECT * FROM reviews", new ReviewRowMapper()
        );
        return reviews;
    }

    public List<Review> findReviewsByUserId(Long userid) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("userid", userid);
        List<Review> reviews = null;
        try {
            reviews = namedParameterJdbcTemplate.query(
                    "SELECT * FROM reviews WHERE userid = :userid",
                    namedParameter, new ReviewRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Review with user id " + userid + " not found");
        }
        return reviews;
    }

    public List<Review> findReviewsByHouseId(Long houseid) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("houseid", houseid);
        List<Review> reviews = null;
        try {
            reviews = namedParameterJdbcTemplate.query(
                    "SELECT * FROM reviews WHERE houseid = :houseid",
                    namedParameter, new ReviewRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Review with house id " + houseid + " not found");
         }
        return reviews;
    }

    public Review update(Review review) {
        SqlParameterSource namedParameter = new MapSqlParameterSource()
                .addValue("id", review.getId())
                .addValue("userid", review.getUser().getId())
                .addValue("houseid", review.getHouse().getId())
                .addValue("stars", review.getStars())
                .addValue("title", review.getTitle())
                .addValue("content", review.getContent())
                .addValue("createdat", review.getCreatedat()
                );
        namedParameterJdbcTemplate.update(
                "UPDATE reviews SET userid = :userid, houseid = :houseid, stars = :stars, title = :title, content = :content, createdat = :createdat",
                namedParameter
        );
        return review;
    }

    public void delete(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(
                "DELETE FROM reviews WHERE id = :id", namedParameter
        );
    }
}
