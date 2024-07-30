package lim.seyeon.safe.stay.infrastructure.RowMapper;

import lim.seyeon.safe.stay.domain.Like.Like;
import lim.seyeon.safe.stay.domain.Post.Post;
import lim.seyeon.safe.stay.domain.User.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LikeRowMapper implements RowMapper<Like> {
    @Override
    public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
        Like like = new Like();
        like.setId(rs.getLong("id"));

        User user = new User();
        user.setId(rs.getLong("user_id"));
        like.setUser(user);

        Post post = new Post();
        post.setId(rs.getLong("post_id"));
        like.setPost(post);

        // Handle possible null value for liked_at
        Timestamp likedAtTimestamp = rs.getTimestamp("liked_at");
        if (likedAtTimestamp != null) {
            like.setLikedAt(likedAtTimestamp.toLocalDateTime());
        } else {
            like.setLikedAt(null); // Or set to a default value
        }

        return like;
    }
}
