package lim.seyeon.safe.stay.application;

import lim.seyeon.safe.stay.domain.House;
import lim.seyeon.safe.stay.domain.HouseRepository;
import lim.seyeon.safe.stay.presentation.HouseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {

    private HouseRepository houseRepository;
    private ValidationService validationService;

    @Autowired
    HouseService(HouseRepository houseRepository, ValidationService validationService) {
        this.houseRepository = houseRepository;
        this.validationService = validationService;
    }

    public HouseDTO add(HouseDTO houseDTO) {
        House house = HouseDTO.toEntity(houseDTO);
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

    public HouseDTO update(HouseDTO houseDTO) {
        House updatedHouse = houseRepository.update(HouseDTO.toEntity(houseDTO));
        HouseDTO updatedHouseDTO = HouseDTO.toDTO(updatedHouse);
        return updatedHouseDTO;
    }

    public void delete(Long id) {
        houseRepository.delete(id);
    }
}
