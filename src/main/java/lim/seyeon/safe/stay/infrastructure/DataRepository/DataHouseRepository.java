package lim.seyeon.safe.stay.infrastructure.DataRepository;

import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.domain.House.House;
import lim.seyeon.safe.stay.domain.House.HouseRepository;
import lim.seyeon.safe.stay.infrastructure.RowMapper.HouseRowMapper;
import lim.seyeon.safe.stay.presentation.Filter.HouseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        SqlParameterSource namedParameter = new MapSqlParameterSource()
                .addValue("id", house.getId())
                .addValue("name", house.getName())
                .addValue("address", house.getAddress())
                .addValue("city", house.getCity())
                .addValue("state", house.getState())
                .addValue("zipcode", house.getZipcode())
                .addValue("price", house.getPrice())
                .addValue("description", house.getDescription())
                .addValue("neighborhood", house.getNeighborhood().getName());
        namedParameterJdbcTemplate.update(
                "INSERT INTO houses (id, name, address, city, state, zipcode, price, description, neighborhood) VALUES (:id, :name, :address, :city, :state, :zipcode, :price, :description, :neighborhood)",
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
                    "SELECT * FROM houses WHERE id = :id",
                    namedParameter, new HouseRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("House with id " + id + " not found");
        }
        return house;
    }

    @Override
    public List<House> findAll() {
        List<House> houses = namedParameterJdbcTemplate.query(
                "SELECT * FROM houses", new HouseRowMapper()
        );
        return houses;
    }

    @Override
    public List<House> findHouseByName(String name) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("name", "%" + name + "%");
        List<House> houses = namedParameterJdbcTemplate.query(
                //LIKE for partial matching
                "SELECT * FROM houses WHERE name LIKE :name",
                namedParameter, new HouseRowMapper()
        );
        return houses;
    }

    @Override
    public List<House> findHouseByNeighborhood(String neighborhood) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("neighborhood", neighborhood);
        List<House> houses = namedParameterJdbcTemplate.query(
                "SELECT * FROM houses WHERE neighborhood = :neighborhood",
                namedParameter, new HouseRowMapper()
        );
        return houses;
    }

    @Override
    public List<House> findHouses(HouseFilter filter) {
        String baseSql = "SELECT * FROM houses WHERE 1=1";
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        if(filter.getName() != null && !filter.getName().isEmpty()) {
            baseSql += " AND name LIKE :name";
            parameters.addValue("name", "%" + filter.getName() + "%");
        }
        if(filter.getNeighborhood() != null && !filter.getNeighborhood().isEmpty()) {
            baseSql += " AND neighborhood LIKE :neighborhood";
            parameters.addValue("neighborhood", "%" + filter.getNeighborhood() + "%");
        }
        if(filter.getMinPrice() != null) {
            baseSql += " AND price >= :minPrice";
            parameters.addValue("minPrice", filter.getMinPrice());
        }
        if(filter.getMaxPrice() != null) {
            baseSql += " AND price <= :maxPrice";
            parameters.addValue("maxPrice", filter.getMaxPrice());
        }

        return namedParameterJdbcTemplate.query(baseSql, parameters, new HouseRowMapper());
    }

    @Override
    public House update(House house) {
        SqlParameterSource namedParameter2 = new BeanPropertySqlParameterSource(house);
        int rowsAffected = namedParameterJdbcTemplate.update(
                "UPDATE houses SET name = :name, address = :address, city = :city, state = :state, zipcode = :zipcode, price = :price, description = :description WHERE id = :id",
                namedParameter2
        );
        if(rowsAffected == 0) {
            throw new EntityNotFoundException("House with id " + house.getId() + " not found");
        }
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
