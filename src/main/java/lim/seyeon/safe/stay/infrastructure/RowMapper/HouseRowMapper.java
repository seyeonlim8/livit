package lim.seyeon.safe.stay.infrastructure.RowMapper;

import lim.seyeon.safe.stay.domain.House.House;
import lim.seyeon.safe.stay.domain.Neighborhood.Neighborhood;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HouseRowMapper implements RowMapper<House> {

    @Override
    public House mapRow(ResultSet rs, int rowNum) throws SQLException {
        House house = new House();
        house.setId(rs.getLong("id"));
        house.setName(rs.getString("name"));
        house.setAddress(rs.getString("address"));
        house.setCity(rs.getString("city"));
        house.setState(rs.getString("state"));
        house.setZipcode(rs.getString("zipcode"));
        house.setPrice(rs.getDouble("price"));
        house.setDescription(rs.getString("description"));

        Neighborhood neighborhood = new Neighborhood();
        neighborhood.setName(rs.getString("neighborhood"));
        house.setNeighborhood(neighborhood);

        return house;
    }
}
