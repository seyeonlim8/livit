package lim.seyeon.safe.stay.infrastructure.DataRepository;

import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.domain.Photo.PhotoRepository;
import lim.seyeon.safe.stay.domain.Post.Post;
import lim.seyeon.safe.stay.domain.Post.PostRepository;
import lim.seyeon.safe.stay.infrastructure.RowMapper.PostRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataPostRepository implements PostRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private PhotoRepository photoRepository;

    @Autowired
    public DataPostRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, PhotoRepository photoRepository) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.photoRepository = photoRepository;
    }

    public Post add(Post post) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameter = new MapSqlParameterSource()
                .addValue("id", post.getId())
                .addValue("title", post.getTitle())
                .addValue("content", post.getContent())
                .addValue("createdAt", post.getCreatedAt())
                .addValue("updatedAt", post.getUpdatedAt())
                .addValue("userId", post.getUser().getId())
                .addValue("categoryId", post.getCategory().getId()
                );
        namedParameterJdbcTemplate.update(
                "INSERT INTO posts (id, title, content, created_at, updated_at, user_id, category_id) "
                    + "VALUES (:id, :title, :content, :createdAt, :updatedAt, :userId, :categoryId)",
                namedParameter, keyHolder
        );
        Long generatedId = keyHolder.getKey().longValue();
        post.setId(generatedId);
        return post;
    }

    @Override
    public Post findPostById(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
        Post post = null;

        try {
            post = namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM posts WHERE id = :id",
                    namedParameter, new PostRowMapper(photoRepository)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Post with id " + id + " not found");
        }
        return post;
    }

    @Override
    public List<Post> findPostByUserId(Long userId) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("userId", userId);
        List<Post> posts = namedParameterJdbcTemplate.query(
                "SELECT * FROM posts WHERE user_id = :userId",
                namedParameter, new PostRowMapper(photoRepository)
        );
        return posts;
    }

    @Override
    public List<Post> findPostByCategoryId(Long categoryId) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("categoryId", categoryId);
        List<Post> posts = namedParameterJdbcTemplate.query(
                "SELECT * FROM posts WHERE category_id = :categoryId",
                namedParameter, new PostRowMapper(photoRepository)
        );
        return posts;
    }

    @Override
    public List<Post> findAllPosts() {
        List<Post> posts = namedParameterJdbcTemplate.query(
                "SELECT * FROM posts", new PostRowMapper(photoRepository)
        );
        return posts;
    }

    public Post update(Post post) {
        SqlParameterSource namedParameter = new MapSqlParameterSource()
                .addValue("id", post.getId())
                .addValue("title", post.getTitle())
                .addValue("content", post.getContent())
                .addValue("createdAt", post.getCreatedAt())
                .addValue("updatedAt", post.getUpdatedAt())
                .addValue("userId", post.getUser().getId())
                .addValue("categoryId", post.getCategory().getId()
                );
        int rowsAffected = namedParameterJdbcTemplate.update(
                "UPDATE posts SET title = :title, content = :content, created_at = :createdAt, updated_at = :updatedAt, user_id = :userId, category_id = :categoryId WHERE id = :id",
                namedParameter
        );

        if (rowsAffected == 0) {
            throw new EntityNotFoundException("Post with id " + post.getId() + " not found");
        }
        return post;
    }

    public void delete(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(
                "DELETE FROM posts WHERE id = :id", namedParameter
        );
    }

}
