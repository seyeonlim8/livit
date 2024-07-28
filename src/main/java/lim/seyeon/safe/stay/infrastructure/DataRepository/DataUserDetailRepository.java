package lim.seyeon.safe.stay.infrastructure.DataRepository;

import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.domain.UserDetail.UserDetail;
import lim.seyeon.safe.stay.domain.UserDetail.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataUserDetailRepository implements UserDetailRepository {

    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DataUserDetailRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public UserDetail add(UserDetail userDetail) {
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(userDetail);
        namedParameterJdbcTemplate.update(
                "INSERT INTO user_name_and_email (user_id, full_name, email) VALUES (:userId, :fullName, :email)",
                namedParameter
        );
        return userDetail;
    }

    public UserDetail findUserDetailByUserId(Long userId) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("userId", userId);
        UserDetail userDetail = null;

        try {
            userDetail = namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM user_name_and_email WHERE user_id = :userId",
                    namedParameter, new BeanPropertyRowMapper<>(UserDetail.class)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("User with id " + userId + " has no user details");
        }

        return userDetail;
    }

    public List<UserDetail> findAll() {
        List<UserDetail> userDetails = namedParameterJdbcTemplate.query(
                "SELECT * FROM user_name_and_email",
                new BeanPropertyRowMapper<>(UserDetail.class)
        );
        return userDetails;
    }

    public UserDetail update(UserDetail userDetail) {
        Long userId = userDetail.getUserId();
        findUserDetailByUserId(userId);

        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(userDetail);
        int rowsAffected = namedParameterJdbcTemplate.update(
                "UPDATE user_name_and_email SET full_name = :fullName, email = :email WHERE user_id = :userId",
                namedParameter
        );
        if(rowsAffected == 0) {
            throw new EntityNotFoundException("User with id " + userId + " has no user details");
        }
        return userDetail;
    }

    public void delete(Long userId) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("userId", userId);
        namedParameterJdbcTemplate.update(
                "DELETE FROM user_name_and_email WHERE user_id = :userId",
                namedParameter
        );
    }
}
