package lim.seyeon.safe.stay.domain.House;

import lim.seyeon.safe.stay.presentation.DTO.HouseFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

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
