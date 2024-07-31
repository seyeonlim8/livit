package lim.seyeon.safe.stay.domain.House;

import lim.seyeon.safe.stay.presentation.Filter.HouseFilter;

import java.util.List;

public interface HouseRepository {
    House add(House house);
    House findHouseById(Long id);
    List<House> findAll();
    List<House> findHouseByName(String name);
    List<House> findHouseByNeighborhood(String neighborhood);
    List<House> findHouses(HouseFilter filter);
    House update(House house);
    void delete(Long id);
}
