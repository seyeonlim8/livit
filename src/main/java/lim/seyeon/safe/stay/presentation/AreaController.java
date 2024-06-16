package lim.seyeon.safe.stay.presentation;

import lim.seyeon.safe.stay.application.AreaService;
import lim.seyeon.safe.stay.domain.Area;
import lim.seyeon.safe.stay.domain.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/area")
public class AreaController {

    private AreaService areaService;

    @Autowired
    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @PostMapping("/upload")
    public AreaDTO upload(@RequestParam AreaDTO areaDTO) {
        return areaService.add(areaDTO);
    }

    @GetMapping("/find")
    public List<AreaDTO> findAreas() {
        return areaService.findAll();
    }

    @GetMapping("/find/{area_num}")
    public AreaDTO findArea(@PathVariable Integer area_num) {
        return areaService.findAreaByAreaNum(area_num);
    }

    @GetMapping("/{area_num}/name")
    public String findAreaName(@PathVariable Integer area_num) {
        return areaService.findAreaByAreaNum(area_num).getName();
    }

    @GetMapping("/{area_num}/population")
    public Long findAreaPopulation(@PathVariable Integer area_num) {
        return areaService.findAreaByAreaNum(area_num).getPopulation();
    }

    @GetMapping("/{area_num}/safety-score")
    public Double findAreaSafetyScore(@PathVariable Integer area_num) {
        return null;
    }

    @PutMapping("/{area_num}/update")
    public AreaDTO update(@RequestParam AreaDTO areaDTO) {
        return areaService.update(areaDTO);
    }

    @DeleteMapping("/{area_num}/delete")
    public void deleteArea(@PathVariable Integer area_num) {
        areaService.delete(area_num);
    }
}
