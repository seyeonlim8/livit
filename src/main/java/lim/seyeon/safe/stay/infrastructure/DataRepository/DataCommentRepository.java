package lim.seyeon.safe.stay.infrastructure.DataRepository;

import lim.seyeon.safe.stay.domain.Comment.Comment;
import lim.seyeon.safe.stay.domain.Comment.CommentRepository;
import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.infrastructure.RowMapper.CommentRowMapper;
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
public class DataCommentRepository implements CommentRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DataCommentRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Comment add(Comment comment) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameter = new MapSqlParameterSource()
                .addValue("id", comment.getId())
                .addValue("content", comment.getContent())
                .addValue("createdAt", comment.getCreatedAt())
                .addValue("userId", comment.getUser().getId())
                .addValue("postId", comment.getPost().getId()
                );
        namedParameterJdbcTemplate.update(
                "INSERT INTO comments (id, content, created_at, user_id, post_id) VALUES (:id, :content, :createdAt, :userId, :postId)",
                namedParameter, keyHolder
        );
        Long generatedId = keyHolder.getKey().longValue();
        comment.setId(generatedId);
        return comment;
    }

    public Comment findCommentById(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
        Comment comment = null;

        try {
            comment = namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM comments WHERE id = :id",
                    namedParameter, new CommentRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Comment with id " + id + " not found");
        }
        return comment;
    }

    public List<Comment> findCommentsByUserId(Long userId) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("userId", userId);
        List<Comment> comments = namedParameterJdbcTemplate.query(
                "SELECT * FROM comments WHERE user_id = :userId",
                namedParameter, new CommentRowMapper()
        );
        return comments;
    }

    public List<Comment> findCommentsByPostId(Long postId) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("postId", postId);
        List<Comment> comments = namedParameterJdbcTemplate.query(
                "SELECT * FROM comments WHERE post_id = :postId",
                namedParameter, new CommentRowMapper()
        );
        return comments;
    }

    public List<Comment> findAllComments() {
        List<Comment> comments = namedParameterJdbcTemplate.query(
                "SELECT * FROM comments", new CommentRowMapper()
        );
        return comments;
    }

    public Comment update(Comment comment) {
        SqlParameterSource namedParameter = new MapSqlParameterSource()
                .addValue("id", comment.getId())
                .addValue("content", comment.getContent())
                .addValue("createdAt", comment.getCreatedAt())
                .addValue("userId", comment.getUser().getId())
                .addValue("postId", comment.getPost().getId()
                );
        int rowsAffected = namedParameterJdbcTemplate.update(
                "UPDATE comments SET content = :content WHERE id = :id",
                namedParameter
        );
        if (rowsAffected == 0) {
            throw new EntityNotFoundException("Comment with id " + comment.getId() + " not found");
        }
        return comment;
    }

    public void delete(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(
                "DELETE FROM comments WHERE id = :id",
                namedParameter
        );
    }
}
