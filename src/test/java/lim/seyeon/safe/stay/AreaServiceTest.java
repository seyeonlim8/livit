package lim.seyeon.safe.stay;

import lim.seyeon.safe.stay.application.AreaService;
import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.presentation.DTO.AreaDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class AreaServiceTest {

    @Autowired
    private AreaService areaService;

    @Test
    @DisplayName("Retrieves the newly added area when area num is queried")
    void addAndFindAreaByAreaNumTest() {
        AreaDTO areaDTO = new AreaDTO(22,"neighborhood1");

        AreaDTO savedAreaDTO = areaService.add(areaDTO);
        AreaDTO foundAreaDTO = areaService.findAreaByAreaNum(22);

        assertEquals(savedAreaDTO.getArea_num(), foundAreaDTO.getArea_num());
        assertEquals(savedAreaDTO.getName(), foundAreaDTO.getName());
    }

    @Test
    @DisplayName("Throws EntityNotFoundException when a non-existent area num is queried")
    void throwsEntityNotFoundExceptionTest() {
        Integer notFoundNum = -1;
        assertThrows(EntityNotFoundException.class, () -> areaService.findAreaByAreaNum(notFoundNum));
    }

    @Test
    @DisplayName("All areas should be retrieved")
    void findAllAreasTest() {
        AreaDTO areaDTO1 = new AreaDTO(22,"neighborhood1");
        AreaDTO areaDTO2 = new AreaDTO(23,"neighborhood2");

        areaService.add(areaDTO1);
        areaService.add(areaDTO2);
        List<AreaDTO> areas = areaService.findAll();

        assertEquals(23, areas.size());
    }

    @Test
    @DisplayName("Area should be successfully updated")
    void updateAreaTest() {
        AreaDTO areaDTO = new AreaDTO(22,"neighborhood1");
        AreaDTO addedArea = areaService.add(areaDTO);

        addedArea.setName("updatedName");
        AreaDTO updatedArea = areaService.update(addedArea);

        assertEquals("updatedName", updatedArea.getName());
    }

    @Test
    @DisplayName("Area should be successfully deleted")
    void deleteAreaTest() {
        AreaDTO areaDTO = new AreaDTO(22,"neighborhood1");
        areaService.add(areaDTO);

        areaService.delete(areaDTO.getArea_num());
        assertThrows(EntityNotFoundException.class, () -> areaService.findAreaByAreaNum(22));
    }
}
