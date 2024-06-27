package lim.seyeon.safe.stay.application;

import lim.seyeon.safe.stay.domain.House.House;
import lim.seyeon.safe.stay.domain.House.HouseRepository;
import lim.seyeon.safe.stay.domain.Neighborhood.Neighborhood;
import lim.seyeon.safe.stay.presentation.DTO.HouseDTO;
import lim.seyeon.safe.stay.presentation.DTO.HouseFilter;
import lim.seyeon.safe.stay.presentation.DTO.NeighborhoodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {

    private NeighborhoodService neighborhoodService;
    private HouseRepository houseRepository;
    private ValidationService validationService;

    @Autowired
    HouseService(HouseRepository houseRepository, ValidationService validationService, NeighborhoodService neighborhoodService) {
        this.houseRepository = houseRepository;
        this.validationService = validationService;
        this.neighborhoodService = neighborhoodService;
    }

    public HouseDTO add(HouseDTO houseDTO) {
        NeighborhoodDTO neighborhoodDTO = neighborhoodService.findNeighborhoodByName(houseDTO.getNeighborhood());
        Neighborhood neighborhood = NeighborhoodDTO.toEntity(neighborhoodDTO);
        House house = HouseDTO.toEntity(houseDTO, neighborhood);
        validationService.checkValid(house);

        House savedHouse = houseRepository.add(house);
        HouseDTO savedHouseDTO = HouseDTO.toDTO(savedHouse);
        return savedHouseDTO;
    }

    public HouseDTO findHouseById(Long id) {
        House house = houseRepository.findHouseById(id);
        HouseDTO houseDTO = HouseDTO.toDTO(house);
        return houseDTO;
    }

    public List<HouseDTO> findHouseByNeighborhood(String neighborhood) {
        List<House> houses = houseRepository.findHouseByNeighborhood(neighborhood);
        List<HouseDTO> housesDTO = houses.stream()
                .map(house -> HouseDTO.toDTO(house))
                .toList();
        return housesDTO;
    }

    public List<HouseDTO> findAll() {
        List<House> houses = houseRepository.findAll();
        List<HouseDTO> housesDTO = houses.stream()
                .map(house -> HouseDTO.toDTO(house))
                .toList();
        return housesDTO;
    }

    public List<HouseDTO> findHouseByNameContaining(String name) {
        List<House> houses = houseRepository.findHouseByName(name);
        List<HouseDTO> housesDTO = houses.stream()
                .map(house -> HouseDTO.toDTO(house))
                .toList();
        return housesDTO;
    }

    public List<HouseDTO> findHouses(HouseFilter filter) {
        List<House> houses = houseRepository.findHouses(filter);
        return houses.stream()
                .map(house -> HouseDTO.toDTO(house))
                .toList();
    }

    public HouseDTO update(HouseDTO houseDTO) {
        NeighborhoodDTO neighborhoodDTO = neighborhoodService.findNeighborhoodByName(houseDTO.getNeighborhood());
        Neighborhood neighborhood = NeighborhoodDTO.toEntity(neighborhoodDTO);
        House house = HouseDTO.toEntity(houseDTO, neighborhood);

        House updatedHouse = houseRepository.update(house);
        HouseDTO updatedHouseDTO = HouseDTO.toDTO(updatedHouse);
        return updatedHouseDTO;
    }

    public void delete(Long id) {
        houseRepository.delete(id);
    }
}
