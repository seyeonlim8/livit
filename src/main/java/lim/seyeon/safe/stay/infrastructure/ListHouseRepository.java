package lim.seyeon.safe.stay.infrastructure;

import lim.seyeon.safe.stay.domain.House;
import lim.seyeon.safe.stay.domain.HouseRepository;
import lim.seyeon.safe.stay.domain.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
/*
@Repository
public class ListHouseRepository implements HouseRepository {

    private List<House> houses = new CopyOnWriteArrayList<>();
    private AtomicLong sequence = new AtomicLong(1L);

    public House add(House house) {
        house.setId(sequence.getAndAdd(1L));
        houses.add(house);
        return house;
    }

    public House findHouseById(Long id) {
        return houses.stream()
                .filter(house -> house.sameId(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("House with id " + id + "is not found."));
    }

    public List<House> findAll() {
        return houses;
    }

    public List<House> findHouseByName(String name) {
        return houses.stream()
                .filter(house -> house.containsName(name))
                .toList();
    }

    public House update(House house) {
        Integer index = houses.indexOf(house);
        houses.set(index, house);
        return house;
    }

    public void delete(Long id) {
        House houseToDelete = this.findHouseById(id);
        houses.remove(houseToDelete);
    }
}
*/