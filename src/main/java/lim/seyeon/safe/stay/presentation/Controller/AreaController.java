package lim.seyeon.safe.stay.presentation.Controller;

import lim.seyeon.safe.stay.application.AreaService;
import lim.seyeon.safe.stay.presentation.DTO.AreaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
public class AreaController {

    private AreaService areaService;

    @Autowired
    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @PostMapping("/upload")
    public AreaDTO upload(@RequestBody AreaDTO areaDTO) {
        return areaService.add(areaDTO);
    }

    @GetMapping
    public List<AreaDTO> findAreas() {
        return areaService.findAll();
    }

    @GetMapping("/{area_num}")
    public AreaDTO findArea(@PathVariable Integer area_num) {
        return areaService.findAreaByAreaNum(area_num);
    }

    @GetMapping("/{area_num}/name")
    public String findAreaName(@PathVariable Integer area_num) {
        return areaService.findAreaByAreaNum(area_num).getName();
    }

    @PutMapping("/{area_num}")
    public AreaDTO update(@RequestBody AreaDTO areaDTO) {
        return areaService.update(areaDTO);
    }

    @DeleteMapping("/{area_num}")
    public void deleteArea(@PathVariable Integer area_num) {
        areaService.delete(area_num);
    }
}
