package lim.seyeon.safe.stay.infrastructure.DataRepository;

import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.domain.RoommatePreference.RoommatePreference;
import lim.seyeon.safe.stay.domain.RoommatePreference.RoommatePreferenceRepository;
import lim.seyeon.safe.stay.infrastructure.RowMapper.RoommatePreferenceRowMapper;
import lim.seyeon.safe.stay.presentation.DTO.RoommateFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public boolean exists(Long user_id) {
       SqlParameterSource namedParameter = new MapSqlParameterSource("userId", user_id);
       boolean isInTable = namedParameterJdbcTemplate.queryForObject(
               "SELECT EXISTS(SELECT 1 FROM roommate_preferences WHERE user_id = :userId) AS user_exists;",
               namedParameter, Boolean.class
       );
       return isInTable;
    }

    @Override
    public List<RoommatePreference> findRoommates(RoommateFilter filter) {
        String baseSql = "SELECT * FROM roommate_preferences WHERE 1=1";
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        Map<String, Integer> filterColumns = new HashMap<>();
        if (filter.getBedtime() != null) filterColumns.put("bedtime", filter.getBedtime());
        if (filter.getNoise() != null) filterColumns.put("noise", filter.getNoise());
        if (filter.getCleanliness() != null) filterColumns.put("cleanliness", filter.getCleanliness());
        if (filter.getVisitors() != null) filterColumns.put("visitors", filter.getVisitors());
        if (filter.getSmoking() != null) filterColumns.put("smoking", filter.getSmoking());
        if (filter.getDrinking() != null) filterColumns.put("drinking", filter.getDrinking());
        if (filter.getPets() != null) filterColumns.put("pets", filter.getPets());
        if (filter.getInteraction() != null) filterColumns.put("interaction", filter.getInteraction());
        if (filter.getGender() != null) filterColumns.put("gender", filter.getGender());
        if (filter.getCulture() != null) filterColumns.put("culture", filter.getCulture());
        if (filter.getLang() != null) filterColumns.put("lang", filter.getLang());

        for (Map.Entry<String, Integer> entry : filterColumns.entrySet()) {
            baseSql += " AND " + entry.getKey() + " = " + entry.getValue();
            parameters.addValue(entry.getKey(), entry.getValue());
        }

        return namedParameterJdbcTemplate.query(baseSql, parameters, new RoommatePreferenceRowMapper());
    }
}
