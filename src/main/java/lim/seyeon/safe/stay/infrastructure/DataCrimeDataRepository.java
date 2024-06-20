package lim.seyeon.safe.stay.infrastructure;

import lim.seyeon.safe.stay.domain.CrimeData.CrimeData;
import lim.seyeon.safe.stay.domain.CrimeData.CrimeDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataCrimeDataRepository implements CrimeDataRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DataCrimeDataRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public CrimeData add(CrimeData crimeData) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(crimeData);

        String sql = "INSERT INTO crime_datas (dr_no, date_rptd, date_occ, time_occ, area, area_name, rpt_dist_no, part_12, crm_cd, crm_cd_desc, mocodes, vict_age, vict_sex, vict_descent, premis_cd, premis_desc, weapon_used, weapon_desc, status, status_desc, crm_cd_1, crm_cd_2, crm_cd_3, crm_cd_4, location, cross_street, lat, lon) " +
                "VALUES (:dr_no, :date_rptd, :date_occ, :time_occ, :area, :area_name, :rpt_dist_no, :part_12, :crm_cd, :crm_cd_desc, :mocodes, :vict_age, :vict_sex, :vict_descent, :premis_cd, :premis_desc, :weapon_used, :weapon_desc, :status, :status_desc, :crm_cd_1, :crm_cd_2, :crm_cd_3, :crm_cd_4, :location, :cross_street, :lat, :lon)";

        namedParameterJdbcTemplate.update(sql, namedParameters);
        return crimeData;
    }

    @Override
    public List<CrimeData> findCrimeDataByAreaNum(Integer area) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("area", area);
        List<CrimeData> crimes = namedParameterJdbcTemplate.query(
                "SELECT * FROM crime_datas WHERE area = :area",
                namedParameters, new BeanPropertyRowMapper<>(CrimeData.class)
        );
        return crimes;
    }

    @Override
    public void deleteAllCrimes() {
        namedParameterJdbcTemplate.getJdbcTemplate().execute(
                "DELETE FROM crime_datas"
        );
    }
}
