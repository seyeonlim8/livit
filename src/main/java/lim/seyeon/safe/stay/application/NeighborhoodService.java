package lim.seyeon.safe.stay.application;

import lim.seyeon.safe.stay.domain.CrimeData.CrimeDataRepository;
import lim.seyeon.safe.stay.domain.Neighborhood.Neighborhood;
import lim.seyeon.safe.stay.domain.Neighborhood.NeighborhoodRepository;
import lim.seyeon.safe.stay.presentation.DTO.NeighborhoodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NeighborhoodService {

    private NeighborhoodRepository neighborhoodRepository;
    private CrimeDataRepository crimeDataRepository;
    private ValidationService validationService;

    @Autowired
    NeighborhoodService(NeighborhoodRepository neighborhoodRepository, CrimeDataRepository crimeDataRepository, ValidationService validationService) {
        this.neighborhoodRepository = neighborhoodRepository;
        this.crimeDataRepository = crimeDataRepository;
        this.validationService = validationService;
    }

    public NeighborhoodDTO add(NeighborhoodDTO neighborhoodDTO) {
        Neighborhood neighborhood = NeighborhoodDTO.toEntity(neighborhoodDTO);
        validationService.checkValid(neighborhood);

        Neighborhood savedNeighborhood = neighborhoodRepository.add(neighborhood);
        NeighborhoodDTO savedNeighborhoodDTO = NeighborhoodDTO.toDTO(savedNeighborhood);
        return savedNeighborhoodDTO;
    }

    public NeighborhoodDTO findNeighborhoodByName(String name) {
        Neighborhood foundNeighborhood = neighborhoodRepository.findNeighborhoodByName(name);
        NeighborhoodDTO foundNeighborhoodDTO = NeighborhoodDTO.toDTO(foundNeighborhood);
        return foundNeighborhoodDTO;
    }

    public List<NeighborhoodDTO> findAll() {
        List<Neighborhood> neighborhoods = neighborhoodRepository.findAll();
        List<NeighborhoodDTO> neighborhoodDTOS = neighborhoods.stream()
                .map(neighborhood -> NeighborhoodDTO.toDTO(neighborhood))
                .toList();
        return neighborhoodDTOS;
    }

    public NeighborhoodDTO update(NeighborhoodDTO neighborhoodDTO) {
        Neighborhood neighborhood = NeighborhoodDTO.toEntity(neighborhoodDTO);
        Neighborhood updatedNeighborhood = neighborhoodRepository.update(neighborhood);
        NeighborhoodDTO updatedNeighborhoodDTO = NeighborhoodDTO.toDTO(updatedNeighborhood);
        return updatedNeighborhoodDTO;
    }

    public void delete(String name) {
        neighborhoodRepository.delete(name);
    }

    public Map<String, Double> calculateAndSetNeighborhoodSafetyScore() {
        return neighborhoodRepository.calculateAndSetNeighborhoodSafetyScores();
    }
}
