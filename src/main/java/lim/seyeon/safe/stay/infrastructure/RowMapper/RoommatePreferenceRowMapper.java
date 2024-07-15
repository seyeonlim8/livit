package lim.seyeon.safe.stay.infrastructure.RowMapper;

import lim.seyeon.safe.stay.domain.RoommatePreference.RoommatePreference;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoommatePreferenceRowMapper implements RowMapper<RoommatePreference> {

    @Override
    public RoommatePreference mapRow(ResultSet rs, int rowNum) throws SQLException {
        RoommatePreference roommatePreference = new RoommatePreference();
        roommatePreference.setUserId(rs.getLong("user_id"));
        roommatePreference.setBedtime(rs.getInt("bedtime"));
        roommatePreference.setNoise(rs.getInt("noise"));
        roommatePreference.setCleanliness(rs.getInt("cleanliness"));
        roommatePreference.setVisitors(rs.getInt("visitors"));
        roommatePreference.setSmoking(rs.getInt("smoking"));
        roommatePreference.setDrinking(rs.getInt("drinking"));
        roommatePreference.setPets(rs.getInt("pets"));
        roommatePreference.setInteraction(rs.getInt("interaction"));
        roommatePreference.setGender(rs.getInt("gender"));
        roommatePreference.setCulture(rs.getInt("culture"));
        roommatePreference.setLang(rs.getInt("lang"));

        return roommatePreference;
    }
}
