package lim.seyeon.safe.stay;

import lim.seyeon.safe.stay.application.HouseService;
import lim.seyeon.safe.stay.domain.EntityNotFoundException;
import lim.seyeon.safe.stay.presentation.HouseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class HouseServiceTest {

    @Autowired
    private HouseService houseService;

    @Test
    @DisplayName("House를 추가한 후 id로 조회하면 해당 House가 조회되어야 한다.")
    void addHouseAndFindByIdTest() {
        HouseDTO houseDTO = new HouseDTO(
                "house1",
                "address1",
                "city1",
                "state1",
                "zip code 1",
                100.0,
                "This is a description.",
                LocalDateTime.now()
        );

        HouseDTO addedHouseDTO = houseService.add(houseDTO);
        Long id = addedHouseDTO.getId();
        HouseDTO foundHouseDTO = houseService.findHouseById(id);

        assertTrue(addedHouseDTO.getId().equals(foundHouseDTO.getId()));
        assertTrue(addedHouseDTO.getAddress().equals(foundHouseDTO.getAddress()));
        assertTrue(addedHouseDTO.getCity().equals(foundHouseDTO.getCity()));
        assertTrue(addedHouseDTO.getState().equals(foundHouseDTO.getState()));
        assertTrue(addedHouseDTO.getZipCode().equals(foundHouseDTO.getZipCode()));
        assertTrue(addedHouseDTO.getDescription().equals(foundHouseDTO.getDescription()));
        assertTrue(addedHouseDTO.getCreatedAt().equals(foundHouseDTO.getCreatedAt()));

    }

    @Test
    @DisplayName("존재하지 않는 id의 house를 조회했을 때 EntityNotFoundException이 던져져야 한다.")
    void throwsEntityNotFoundExceptionTest() {
        Long notFoundId = -1L;
        assertThrows(EntityNotFoundException.class, () -> houseService.findHouseById(notFoundId));
    }

}


