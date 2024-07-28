package lim.seyeon.safe.stay.infrastructure.RowMapper;

import lim.seyeon.safe.stay.domain.Photo.Photo;
import lim.seyeon.safe.stay.domain.Post.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhotoRowMapper implements RowMapper<Photo> {

    @Override
    public Photo mapRow(ResultSet rs, int rowNum) throws SQLException {
        Photo photo = new Photo();
        photo.setId(rs.getLong("id"));
        photo.setUrl(rs.getString("url"));

        Post post = new Post();
        post.setId(rs.getLong("post_id"));
        photo.setPost(post);

        return photo;
    }
}
