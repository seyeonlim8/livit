package lim.seyeon.safe.stay.infrastructure;

import lim.seyeon.safe.stay.domain.EntityNotFoundException;
import lim.seyeon.safe.stay.domain.House;
import lim.seyeon.safe.stay.domain.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataHouseRepository implements HouseRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DataHouseRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public House add(House house) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(house);
        namedParameterJdbcTemplate.update(
                "INSERT INTO houses (id, name, address, city, state, zipcode, price, description) VALUES (:id, :name, :address, :city, :state, :zipcode, :price, :description)",
                namedParameter, keyHolder
        );
        Long generatedId = keyHolder.getKey().longValue();
        house.setId(generatedId);
        return house;
    }

    @Override
    public House findHouseById(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
        House house = null;

        try {
            house = namedParameterJdbcTemplate.queryForObject(
                    "SELECT id, name, address, city, state, zipcode, price, description FROM houses WHERE id = :id",
                    namedParameter, new BeanPropertyRowMapper<>(House.class)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("House with id " + id + " not found");
        }
        return house;
    }

    @Override
    public List<House> findAll() {
        List<House> houses = namedParameterJdbcTemplate.query(
                "SELECT * FROM houses", new BeanPropertyRowMapper<>(House.class)
        );
        return houses;
    }

    @Override
    public List<House> findHouseByName(String name) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("name", "%" + name + "%");
        List<House> houses = namedParameterJdbcTemplate.query(
                //LIKE for partial matching
                "SELECT * FROM houses WHERE name LIKE :name",
                namedParameter, new BeanPropertyRowMapper<>(House.class)
        );
        return houses;
    }

    @Override
    public House update(House house) {
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(house);
        namedParameterJdbcTemplate.update(
                "UPDATE houses SET name = :name, address = :address, city = :city, state = :state, zipcode = :zipcode, price = :price, description = :description WHERE id = :id",
                namedParameter
        );
        return house;
    }

    @Override
    public void delete(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(
                "DELETE FROM houses WHERE id = :id", namedParameter
        );
    }
}
