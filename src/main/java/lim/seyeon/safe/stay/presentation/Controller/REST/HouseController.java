package lim.seyeon.safe.stay.presentation.Controller.REST;

import jakarta.validation.Valid;
import lim.seyeon.safe.stay.application.HouseService;
import lim.seyeon.safe.stay.presentation.DTO.HouseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HouseController {

    private HouseService houseService;

    @Autowired
    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @PostMapping(value = "/houses")
    public HouseDTO createHouse(@Valid @RequestBody HouseDTO houseDTO) {
        return houseService.add(houseDTO);
    }

    @GetMapping(value = "/houses")
    public List<HouseDTO> findHouses(@RequestParam(required = false) String name, @RequestParam(required = false) String neighborhood) {
        if(name == null && neighborhood == null) {
            return houseService.findAll();
        }
        if(name != null && neighborhood == null) {
            return houseService.findHouseByNameContaining(name);
        }
        return houseService.findHouseByNeighborhood(neighborhood);
    }

    @GetMapping(value = "/houses/{id}")
    public HouseDTO findHouseById(@PathVariable Long id) {
        return houseService.findHouseById(id);
    }

    @PutMapping(value = "/houses/{id}")
    public HouseDTO updateHouse(@PathVariable Long id, @RequestBody HouseDTO houseDTO) {
        houseDTO.setId(id);
        return houseService.update(houseDTO);
    }

    @DeleteMapping(value = "/houses/{id}")
    public void deleteHouse(@PathVariable Long id) {
        houseService.delete(id);
    }


}
