package lim.seyeon.safe.stay.infrastructure.DataRepository;

import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.domain.RoommatePreference.RoommatePreference;
import lim.seyeon.safe.stay.domain.RoommatePreference.RoommatePreferenceRepository;
import lim.seyeon.safe.stay.infrastructure.RowMapper.RoommatePreferenceRowMapper;
import lim.seyeon.safe.stay.presentation.DTO.RoommateFilter;
import lim.seyeon.safe.stay.presentation.DTO.RoommatePreferenceDTO;
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

        int rowsAffected = namedParameterJdbcTemplate.update(sql1 + sql2, namedParameter);

        if(rowsAffected == 0) {
            throw new EntityNotFoundException("RoommatePreference for user with id " + roommatePreference.getUserId() + " not found");
        }
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
    public List<RoommatePreference> findRoommates(RoommateFilter filter, Long currentUserId) {
        String baseSql = "SELECT * FROM roommate_preferences WHERE user_id != :currentUserId";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("currentUserId", currentUserId);

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

    @Override
    public Integer getMatchRate(Long user_id1, Long user_id2) {
        SqlParameterSource namedParameter = new MapSqlParameterSource()
                .addValue("user_id1", Math.min(user_id1, user_id2))
                .addValue("user_id2", Math.max(user_id1, user_id2));

        Integer matchRate;
        try {
            matchRate = namedParameterJdbcTemplate.queryForObject(
                    "SELECT match_rate FROM match_rate_cache WHERE user_id1 = :user_id1 AND user_id2 = :user_id2",
                    namedParameter, Integer.class
            );
        } catch (EmptyResultDataAccessException e) {
             matchRate = null;
        }
        return matchRate;
    }

    @Override
    public Integer addMatchRate(Long user_id1, Long user_id2) {
        RoommatePreference userPreference = findRoommatePreferenceByUserId(user_id1);
        RoommatePreference otherUserPreference = findRoommatePreferenceByUserId(user_id2);
        Integer matchRate = calculateMatchRate(userPreference, otherUserPreference);
        SqlParameterSource namedParameter = new MapSqlParameterSource()
                .addValue("user_id1", Math.min(user_id1, user_id2))
                .addValue("user_id2", Math.max(user_id1, user_id2))
                .addValue("matchRate", matchRate);

        namedParameterJdbcTemplate.update(
                "INSERT INTO match_rate_cache (user_id1, user_id2, match_rate) VALUES (:user_id1, :user_id2, :matchRate)"
                + "ON DUPLICATE KEY UPDATE match_rate = :matchRate",
                namedParameter
        );
        return matchRate;
    }

    @Override
    public Integer getQuestionId(String questionKey) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("questionKey", questionKey);
        Integer questionId = namedParameterJdbcTemplate.queryForObject(
                "SELECT id FROM questions WHERE question_key = :questionKey",
                namedParameter, Integer.class
        );
        return questionId;
    }

    @Override
    public String getAnswerText(String questionKey, int answerNum) {
        Integer questionId = getQuestionId(questionKey);
        SqlParameterSource namedParameter = new MapSqlParameterSource()
                .addValue("questionId", questionId)
                .addValue("answerNum", answerNum);
        String answerText = namedParameterJdbcTemplate.queryForObject(
                "SELECT text FROM answers WHERE question_id = :questionId AND answer_num = :answerNum",
                namedParameter, String.class
        );
        return answerText;
    }

    private int calculateMatchRate(RoommatePreference userPreference, RoommatePreference otherUserPreference) {
        int matchScore = 0;
        int numOfQuestions = 11;

        if(userPreference.getBedtime().equals(otherUserPreference.getBedtime())) matchScore++;

        if(userPreference.getNoise().equals(otherUserPreference.getNoise())) matchScore++;

        if(userPreference.getCleanliness().equals(otherUserPreference.getCleanliness())) matchScore++;

        if(userPreference.getVisitors().equals(otherUserPreference.getVisitors())) matchScore++;

        // Both option 2 and 3 don't smoke
        if(userPreference.getSmoking().equals(otherUserPreference.getSmoking())
                || (userPreference.getSmoking() == 1 && otherUserPreference.getSmoking() == 2)
                || (userPreference.getSmoking() == 2 && otherUserPreference.getSmoking() == 1)
                || (userPreference.getSmoking() == 2 && otherUserPreference.getSmoking() == 3)
                || userPreference.getSmoking() == 3 && otherUserPreference.getSmoking() == 2) matchScore++;
        // Both option 2 and 3 don't drink
        if(userPreference.getDrinking().equals(otherUserPreference.getDrinking())
                || (userPreference.getDrinking() == 1 && otherUserPreference.getDrinking() == 2)
                || (userPreference.getDrinking() == 2 && otherUserPreference.getDrinking() == 1)
                || (userPreference.getDrinking() == 2 && otherUserPreference.getDrinking() == 3)
                || userPreference.getDrinking() == 3 && otherUserPreference.getDrinking() == 2) matchScore++;
        // Both option 2 and 3 don't have ptes
        if(userPreference.getPets().equals(otherUserPreference.getPets())
                || (userPreference.getPets() == 1 && otherUserPreference.getPets() == 2)
                || (userPreference.getPets() == 2 && otherUserPreference.getPets() == 1)
                || (userPreference.getPets() == 2 && otherUserPreference.getPets() == 3)
                || userPreference.getPets() == 3 && otherUserPreference.getPets() == 2) matchScore++;

        if(userPreference.getInteraction().equals(otherUserPreference.getInteraction())) matchScore++;

        // Option 3 - no preference
        if(userPreference.getGender().equals(otherUserPreference.getGender())
                || userPreference.getGender() == 3
                || otherUserPreference.getGender() == 3) matchScore++;

        // Option 3 - no preference
        if(userPreference.getCulture().equals(otherUserPreference.getCulture())
                || userPreference.getCulture() == 0
                || otherUserPreference.getCulture() == 0) matchScore++;

        if(userPreference.getLang().equals(otherUserPreference.getLang())
                || userPreference.getLang() == 0
                || otherUserPreference.getLang() == 0) matchScore++;

        // Match rate out of 100
        int matchRate = (matchScore * 100) / numOfQuestions;
        return matchRate;
    }
}
