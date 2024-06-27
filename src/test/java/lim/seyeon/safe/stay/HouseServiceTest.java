package lim.seyeon.safe.stay;

import lim.seyeon.safe.stay.application.HouseService;
import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.presentation.DTO.HouseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class HouseServiceTest {

    @Autowired
    private HouseService houseService;

    @Test
    @DisplayName("Retrieves the newly added house when house id is queried")
    void addHouseAndFindByIdTest() {
        HouseDTO houseDTO = new HouseDTO(
                "house1",
                "address1",
                "city1",
                "state1",
                "zip code 1",
                100.0,
                "This is a description.",
                "Central City"
        );

        HouseDTO addedHouseDTO = houseService.add(houseDTO);
        Long id = addedHouseDTO.getId();
        HouseDTO foundHouseDTO = houseService.findHouseById(id);

        assertEquals(addedHouseDTO.getId(),foundHouseDTO.getId());
        assertEquals(addedHouseDTO.getName(),foundHouseDTO.getName());
        assertEquals(addedHouseDTO.getAddress(),foundHouseDTO.getAddress());
        assertEquals(addedHouseDTO.getCity(),foundHouseDTO.getCity());
        assertEquals(addedHouseDTO.getState(),foundHouseDTO.getState());
        assertEquals(addedHouseDTO.getZipcode(), foundHouseDTO.getZipcode());
        assertEquals(addedHouseDTO.getPrice(), foundHouseDTO.getPrice());
        assertEquals(addedHouseDTO.getDescription(),foundHouseDTO.getDescription());
        assertEquals(addedHouseDTO.getNeighborhood(), foundHouseDTO.getNeighborhood());
    }

    @Test
    @DisplayName("Throws EntityNotFoundException when a non-existent house id is queried")
    void throwsEntityNotFoundExceptionTest() {
        Long notFoundId = -1L;
        assertThrows(EntityNotFoundException.class, () -> houseService.findHouseById(notFoundId));
    }

    @Test
    @DisplayName("All added houses should be retrieved")
    void findAllHousesTest() {
        HouseDTO houseDTO1 = new HouseDTO(
                "house1",
                "address1",
                "city1",
                "state1",
                "zip code 1",
                100.0,
                "This is a description.",
                "Central City"
        );

        HouseDTO houseDTO2 = new HouseDTO(
                "house2",
                "address2",
                "city2",
                "state2",
                "zip code 2",
                200.0,
                "This is another description.",
                "South Los Angeles"
        );

        houseService.add(houseDTO1);
        houseService.add(houseDTO2);

        List<HouseDTO> houses = houseService.findAll();

        assertEquals(2, houses.size());
    }

    @Test
    @DisplayName("Houses containing specific name should be retrievable")
    void findHousesByNameContainingTest() {
        HouseDTO houseDTO1 = new HouseDTO(
                "special house",
                "address1",
                "city1",
                "state1",
                "zip code 1",
                100.0,
                "This is a description.",
                "Central City"
        );

        HouseDTO houseDTO2 = new HouseDTO(
                "ordinary house",
                "address2",
                "city2",
                "state2",
                "zip code 2",
                200.0,
                "This is another description.",
                "South Los Angeles"
        );

        houseService.add(houseDTO1);
        houseService.add(houseDTO2);

        List<HouseDTO> houses = houseService.findHouseByNameContaining("special");

        assertEquals(1, houses.size());
        assertTrue(houses.get(0).getName().contains("special"));
    }

    @Test
    @DisplayName("House should be updated successfully")
    void updateHouseTest() {
        HouseDTO houseDTO = new HouseDTO(
                "house1",
                "address1",
                "city1",
                "state1",
                "zip code 1",
                100.0,
                "This is a description.",
                "Central City"
        );
        HouseDTO addedHouseDTO = houseService.add(houseDTO);

        addedHouseDTO.setName("updated house");
        HouseDTO updatedHouseDTO = houseService.update(addedHouseDTO);

        assertEquals("updated house", updatedHouseDTO.getName());
    }

    @Test
    @DisplayName("House should be deleted successfully")
    void deleteHouseTest() {
        HouseDTO houseDTO = new HouseDTO(
                "house1",
                "address1",
                "city1",
                "state1",
                "zip code 1",
                100.0,
                "This is a description.",
                "Central City"
        );

        HouseDTO addedHouseDTO = houseService.add(houseDTO);
        Long id = addedHouseDTO.getId();

        houseService.delete(id);

        assertThrows(EntityNotFoundException.class, () -> houseService.findHouseById(id));
    }
}



