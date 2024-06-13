package lim.seyeon.safe.stay.presentation;

import jakarta.validation.Valid;
import lim.seyeon.safe.stay.application.HouseService;
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
    public List<HouseDTO> findHouses(@RequestParam(required = false) String name) {
        if(name == null) {
            return houseService.findAll();
        }
        return houseService.findHouseByNameContaining(name);
    }

    @GetMapping(value = "/houses/{id}")
    public HouseDTO findHouseById(@PathVariable Long id, @RequestBody HouseDTO houseDTO) {
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
