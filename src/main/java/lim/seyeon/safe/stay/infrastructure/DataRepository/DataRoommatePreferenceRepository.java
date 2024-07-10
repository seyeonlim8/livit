package lim.seyeon.safe.stay.infrastructure.DataRepository;

import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.domain.RoommatePreference.RoommatePreference;
import lim.seyeon.safe.stay.domain.RoommatePreference.RoommatePreferenceRepository;
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
public class DataRoommatePreferenceRepository implements RoommatePreferenceRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DataRoommatePreferenceRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public RoommatePreference add(RoommatePreference roommatePreference) {
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(roommatePreference);
        String sql1 = "INSERT INTO roommate_preferences (user_id, bedtime, noise, cleanliness, visitors, smoking, drinking, pets, interaction, gender, culture, lang) ";
        String sql2 = "VALUES (:userId, :bedtime, :noise, :cleanliness, :visitors, :smoking, :drinking, :pets, :interaction, :gender, :culture, :lang)";

        namedParameterJdbcTemplate.update(sql1 + sql2, namedParameter);
        return roommatePreference;
    }

    @Override
    public RoommatePreference findRoommatePreferenceByUserId(Long user_id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("userId", user_id);
        RoommatePreference roommatePreference = null;

        try {
            roommatePreference = namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM roommate_preferences WHERE user_id = :userId",
                    namedParameter, new BeanPropertyRowMapper<>(RoommatePreference.class)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("RoommatePreference for user with id " + user_id + " not found");
        }
        return roommatePreference;
    }

    @Override
    public List<RoommatePreference> findAll() {
        List<RoommatePreference> roommatePreferences = namedParameterJdbcTemplate.query(
                "SELECT * FROM roommate_preferences",
                new BeanPropertyRowMapper<>(RoommatePreference.class)
        );
        return roommatePreferences;
    }

    @Override
    public RoommatePreference update(RoommatePreference roommatePreference) {
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(roommatePreference);
        String sql1 = "UPDATE roommate_preferences SET bedtime = :bedtime, noise = :noise, cleanliness = :cleanliness, visitors = :visitors, smoking = :smoking, drinking = :drinking, pets = :pets, interaction = :interaction, gender = :gender, culture = :culture, lang = :lang ";
        String sql2 = "WHERE user_id = :userId";

        namedParameterJdbcTemplate.update(sql1 + sql2, namedParameter);
        return roommatePreference;
    }

    @Override
    public void delete(Long user_id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("userId", user_id);
        namedParameterJdbcTemplate.update(
                "DELETE FROM roommate_preferences WHERE user_id = :userId",
                namedParameter
        );
    }
}
