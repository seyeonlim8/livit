package lim.seyeon.safe.stay.domain.House;

import java.util.List;

public interface HouseRepository {
    House add(House house);
    House findHouseById(Long id);
    List<House> findAll();
    List<House> findHouseByName(String name);
    House update(House house);
    void delete(Long id);
}
