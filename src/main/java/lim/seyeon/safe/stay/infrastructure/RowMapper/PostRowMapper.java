package lim.seyeon.safe.stay.infrastructure.RowMapper;

import lim.seyeon.safe.stay.application.PhotoService;
import lim.seyeon.safe.stay.domain.Category.Category;
import lim.seyeon.safe.stay.domain.Like.Like;
import lim.seyeon.safe.stay.domain.Like.LikeRepository;
import lim.seyeon.safe.stay.domain.Photo.Photo;
import lim.seyeon.safe.stay.domain.Photo.PhotoRepository;
import lim.seyeon.safe.stay.domain.Post.Post;
import lim.seyeon.safe.stay.domain.User.User;
import lim.seyeon.safe.stay.presentation.DTO.PhotoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostRowMapper implements RowMapper<Post> {

    private PhotoRepository photoRepository;

    public PostRowMapper(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();
        post.setId(rs.getLong("id"));
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        post.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

        User user = new User();
        user.setId(rs.getLong("user_id"));
        post.setUser(user);

        Category category = new Category();
        category.setId(rs.getLong("category_id"));
        post.setCategory(category);

        List<Photo> photos = photoRepository.findPhotosByPostId(post.getId());
        post.setPhotos(photos);

        return post;
    }
}
