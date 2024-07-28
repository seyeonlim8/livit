package lim.seyeon.safe.stay.infrastructure.DataRepository;

import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.domain.Photo.Photo;
import lim.seyeon.safe.stay.domain.Photo.PhotoRepository;
import lim.seyeon.safe.stay.infrastructure.RowMapper.PhotoRowMapper;
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
public class DataPhotoRepository implements PhotoRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DataPhotoRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Photo add(Photo photo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameter = new MapSqlParameterSource()
                .addValue("id", photo.getId())
                .addValue("url", photo.getUrl())
                .addValue("postId", photo.getPost().getId());
        namedParameterJdbcTemplate.update(
                "INSERT INTO photos (id, url, post_id) VALUES (:id, :url, :postId)",
                namedParameter, keyHolder
        );
        Long generatedId = keyHolder.getKey().longValue();
        photo.setId(generatedId);
        return photo;
    }

    public Photo findPhotoById(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
        Photo photo = null;

        try {
            photo = namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM photos WHERE id = :id",
                    namedParameter, new PhotoRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Photo with id " + id + " not found");
        }
        return photo;
    }

    public List<Photo> findPhotosByPostId(Long postId) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("postId", postId);
        List<Photo> photos = namedParameterJdbcTemplate.query(
                "SELECT * FROM photos WHERE post_id = :postId",
                namedParameter, new PhotoRowMapper()
        );
        return photos;
    }

    public List<Photo> findAllPhotos() {
        List<Photo> photos = namedParameterJdbcTemplate.query(
                "SELECT * FROM photos", new PhotoRowMapper()
        );
        return photos;
    }

    public Photo update(Photo photo) {
        SqlParameterSource namedParameter = new MapSqlParameterSource()
                .addValue("id", photo.getId())
                .addValue("url", photo.getUrl())
                .addValue("postId", photo.getPost().getId());
        int rowsAffected = namedParameterJdbcTemplate.update(
                "UPDATE photos SET url = :url, post_id = :postId WHERE id = :id",
                namedParameter
        );

        if (rowsAffected == 0) {
            throw new EntityNotFoundException("Photo with id " + photo.getId() + " not found");
        }
        return photo;
    }

    public void delete(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(
                "DELETE FROM photos WHERE id = :id",
                namedParameter);
    }
}
