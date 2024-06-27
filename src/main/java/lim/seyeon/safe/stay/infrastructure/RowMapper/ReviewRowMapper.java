package lim.seyeon.safe.stay.infrastructure.RowMapper;

import lim.seyeon.safe.stay.domain.House.House;
import lim.seyeon.safe.stay.domain.Review.Review;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import lim.seyeon.safe.stay.domain.User.User;

public class ReviewRowMapper implements RowMapper<Review> {

    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        Review review = new Review();
        review.setId(rs.getLong("id"));
        review.setStars(rs.getInt("stars"));
        review.setTitle(rs.getString("title"));
        review.setContent(rs.getString("content"));
        review.setCreatedat(rs.getTimestamp("createdat").toLocalDateTime());

        // Create new User and House instances with only IDs set
        User user = new User();
        user.setId(rs.getLong("userid"));
        review.setUser(user);

        House house = new House();
        house.setId(rs.getLong("houseid"));
        review.setHouse(house);

        return review;
    }
}
