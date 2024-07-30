package lim.seyeon.safe.stay.infrastructure.DataRepository;

import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.domain.Like.Like;
import lim.seyeon.safe.stay.domain.Like.LikeRepository;
import lim.seyeon.safe.stay.infrastructure.RowMapper.LikeRowMapper;
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
public class DataLikeRepository implements LikeRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DataLikeRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Like add(Like like){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameter = new MapSqlParameterSource()
                .addValue("id", like.getId())
                .addValue("userId", like.getUser().getId())
                .addValue("postId", like.getPost().getId())
                .addValue("likedAt", like.getLikedAt());
        namedParameterJdbcTemplate.update(
                "INSERT INTO likes (id, user_id, post_id, liked_at) VALUES (:id, :userId, :postId, :likedAt)",
                namedParameter, keyHolder
        );

        Long generatedId = keyHolder.getKey().longValue();
        like.setId(generatedId);
        return like;
    }

    @Override
    public Like findLikeById(Long id){
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
        Like like = null;

        try {
            like = namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM likes WHERE id = :id",
                    namedParameter, new LikeRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Like with id " + id + " not found");
        }
        return like;
    }

    @Override
    public Like findLikeByPostIdAndUserId(Long postId, Long userId) {
        SqlParameterSource namedParameter = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("postId", postId);
        Like like = null;

        try {
            like = namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM likes WHERE user_id = :userId AND post_id = :postId",
                    namedParameter, new LikeRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Like with postId " + postId + " and userId "+ userId + " not found");
        }
        return like;
    }

    @Override
    public List<Like> findLikesByUserId(Long userId){
        SqlParameterSource namedParameter = new MapSqlParameterSource("userId", userId);
        List<Like> likes = namedParameterJdbcTemplate.query(
                "SELECT * FROM likes WHERE user_id = :userId",
                namedParameter, new LikeRowMapper()
        );
        return likes;
    }

    @Override
    public List<Like> findLikesByPostId(Long postId){
        SqlParameterSource namedParameter = new MapSqlParameterSource("postId", postId);
        List<Like> likes = namedParameterJdbcTemplate.query(
                "SELECT * FROM likes WHERE post_id = :postId",
                namedParameter, new LikeRowMapper()
        );
        return likes;
    }

    @Override
    public List<Like> findAllLikes(){
        List<Like> likes = namedParameterJdbcTemplate.query(
                "SELECT * FROM likes",
                new LikeRowMapper()
        );
        return likes;
    }

    @Override
    public Like update(Like like){
        SqlParameterSource namedParameter = new MapSqlParameterSource()
                .addValue("id", like.getId())
                .addValue("userId", like.getUser().getId())
                .addValue("postId", like.getPost().getId())
                .addValue("likedAt", like.getLikedAt());
        int rowsAffected = namedParameterJdbcTemplate.update(
                "UPDATE likes SET user_id = :userId, post_id = :postId, liked_at = :likedAt WHERE id = :id",
                namedParameter
        );
        if(rowsAffected == 0) {
            throw new EntityNotFoundException("Like with id " + like.getId() + " not found");
        }
        return like;
    }

    @Override
    public void delete(Long id){
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(
                "DELETE FROM likes WHERE id = :id",
                namedParameter);
    }

}
