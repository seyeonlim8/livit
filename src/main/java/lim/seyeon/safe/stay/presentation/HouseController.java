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

    @RequestMapping(value = "/houses", method = RequestMethod.POST)
    public HouseDTO createHouse(@Valid @RequestBody HouseDTO houseDTO) {
        return houseService.add(houseDTO);
    }

    @RequestMapping(value = "/houses", method = RequestMethod.GET)
    public List<HouseDTO> findHouses(@RequestParam(required = false) String name) {
        if(name == null) {
            return houseService.findAll();
        }
        return houseService.findHouseByNameContaining(name);
    }

    @RequestMapping(value = "/houses/{id}", method = RequestMethod.GET)
    public HouseDTO findHouseById(@PathVariable Long id, @RequestBody HouseDTO houseDTO) {
        return houseService.findHouseById(id);
    }

    @RequestMapping(value = "/houses/{id}", method = RequestMethod.PUT)
    public HouseDTO updateHouse(@PathVariable Long id, @RequestBody HouseDTO houseDTO) {
        houseDTO.setId(id);
        return houseService.update(houseDTO);
    }

    @RequestMapping(value = "/houses/{id}", method = RequestMethod.DELETE)
    public void deleteHouse(@PathVariable Long id) {
        houseService.delete(id);
    }
}
