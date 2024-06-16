package lim.seyeon.safe.stay.infrastructure;

import lim.seyeon.safe.stay.domain.CrimeData;
import lim.seyeon.safe.stay.domain.CrimeDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

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

        String sql = "INSERT INTO crime_data (dr_no, date_rptd, date_occ, time_occ, area, area_name, rpt_dist_no, part_12, crm_cd, crm_cd_desc, mocodes, vict_age, vict_sex, vict_descent, premis_cd, premis_desc, weapon_used, weapon_desc, status, status_desc, crm_cd_1, crm_cd_2, crm_cd_3, crm_cd_4, location, cross_street, lat, lon) " +
                "VALUES (:dr_no, :date_rptd, :date_occ, :time_occ, :area, :area_name, :rpt_dist_no, :part_12, :crm_cd, :crm_cd_desc, :mocodes, :vict_age, :vict_sex, :vict_descent, :premis_cd, :premis_desc, :weapon_used, :weapon_desc, :status, :status_desc, :crm_cd_1, :crm_cd_2, :crm_cd_3, :crm_cd_4, :location, :cross_street, :lat, :lon)";

        namedParameterJdbcTemplate.update(sql, namedParameters);
        return crimeData;
    }
}
