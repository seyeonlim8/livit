package lim.seyeon.safe.stay.presentation;

import lim.seyeon.safe.stay.application.NeighborhoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/neighborhoods")
public class NeighborhoodController {

    private NeighborhoodService neighborhoodService;

    @Autowired
    public NeighborhoodController(NeighborhoodService neighborhoodService) {
        this.neighborhoodService = neighborhoodService;
    }

    @PostMapping
    public NeighborhoodDTO add(@RequestBody NeighborhoodDTO neighborhoodDTO) {
        return neighborhoodService.add(neighborhoodDTO);
    }

    @GetMapping
    public NeighborhoodDTO getNeighborhoodByName(@RequestParam("name") String name) {
        return neighborhoodService.findNeighborhoodByName(name);
    }

    @GetMapping("/find-all")
    public List<NeighborhoodDTO> findAll() {
        return neighborhoodService.findAll();
    }

    @GetMapping("/population")
    public Long getPopulationByName(@RequestParam("name") String name) {
        return neighborhoodService.findNeighborhoodByName(name).getPopulation();
    }

    @GetMapping("/safety-score")
    public Double getSafetyScoreByName(@RequestParam("name") String name) {
        return neighborhoodService.findNeighborhoodByName(name).getSafety_score();
    }

    @PutMapping("/safety-score")
    public Map<String, Double> calculateAndSetSafetyScores() {
        return neighborhoodService.calculateAndSetNeighborhoodSafetyScore();
    }

    @PutMapping
    public NeighborhoodDTO update(@RequestBody NeighborhoodDTO neighborhoodDTO) {
        return neighborhoodService.update(neighborhoodDTO);
    }

    @DeleteMapping
    public void delete(@RequestParam("name") String name) {
        neighborhoodService.delete(name);
    }


}
