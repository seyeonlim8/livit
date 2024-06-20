package lim.seyeon.safe.stay.infrastructure;

import lim.seyeon.safe.stay.domain.Area.Area;
import lim.seyeon.safe.stay.domain.Area.AreaRepository;
import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataAreaRepository implements AreaRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DataAreaRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Area add(Area area) {
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(area);

        namedParameterJdbcTemplate.update(
                "INSERT INTO areas (area_num, name) VALUES (:area_num, :name)",
                namedParameter
        );
        return area;
    }

    @Override
    public Area findAreaByAreaNum(Integer area_num) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("area_num", area_num);
        Area area = null;

        try {
            area = namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM areas WHERE area_num = :area_num",
                    namedParameter, new BeanPropertyRowMapper<>(Area.class)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Area with num " + area_num + " not found");
        }
        return area;
    }

    @Override
    public List<Area> findAllAreas() {
        List<Area> areas = namedParameterJdbcTemplate.query(
                "SELECT * FROM areas",
                new BeanPropertyRowMapper<>(Area.class)
        );
        return areas;
    }

    @Override
    public Area update(Area area) {
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(area);
        namedParameterJdbcTemplate.update(
                "UPDATE areas SET name = :name WHERE area_num = :area_num",
                namedParameter
        );
        return area;
    }

    @Override
    public void delete(Integer area_num) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("area_num", area_num);
        namedParameterJdbcTemplate.update(
                "DELETE FROM areas WHERE area_num = :area_num",
                namedParameter
        );
    }
}
