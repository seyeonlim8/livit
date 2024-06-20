package lim.seyeon.safe.stay;

import lim.seyeon.safe.stay.application.NeighborhoodService;
import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.presentation.DTO.NeighborhoodDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class NeighborhoodServiceTest {

    @Autowired
    private NeighborhoodService neighborhoodService;

    @Test
    @DisplayName("Retrieves the newly added neighborhood when neighborhood name is queried")
    void addNeighborhoodAndFindByNeighborhoodNameTest() {
        NeighborhoodDTO neighborhoodDTO = new NeighborhoodDTO("name", 12345L, 12.3);

        NeighborhoodDTO addedNeighborhood = neighborhoodService.add(neighborhoodDTO);
        NeighborhoodDTO foundNeighborhood = neighborhoodService.findNeighborhoodByName("name");

        assertEquals(addedNeighborhood.getName(), foundNeighborhood.getName());
        assertEquals(addedNeighborhood.getPopulation(), foundNeighborhood.getPopulation());
        assertEquals(addedNeighborhood.getSafety_score(), foundNeighborhood.getSafety_score());

    }

    @Test
    @DisplayName("Throws EntityNotFoundException when a non-existent neighborhood name is queried")
    void throwsEntityNotFoundExceptionTest() {
        String nonExistentNeighborhoodName = "nonExistentNeighborhoodName";
        assertThrows(EntityNotFoundException.class, () -> neighborhoodService.findNeighborhoodByName(nonExistentNeighborhoodName));
    }

    @Test
    @DisplayName("Neighborhood should be successfully updated")
    void updateNeighborhoodTest() {
        NeighborhoodDTO neighborhoodDTO = new NeighborhoodDTO("name", 12345L, 12.3);
        neighborhoodService.add(neighborhoodDTO);

        neighborhoodDTO.setName("newName");
        NeighborhoodDTO updatedNeighborhood = neighborhoodService.update(neighborhoodDTO);

        assertEquals("newName", updatedNeighborhood.getName());
    }

    @Test
    @DisplayName("Neighborhood should be deleted successfully")
    void deleteNeighborhoodTest() {
        NeighborhoodDTO neighborhoodDTO = new NeighborhoodDTO("name", 12345L, 12.3);
        neighborhoodService.add(neighborhoodDTO);

        neighborhoodService.delete(neighborhoodDTO.getName());

        assertThrows(EntityNotFoundException.class, () -> neighborhoodService.findNeighborhoodByName("name"));
    }

}
