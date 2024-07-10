package lim.seyeon.safe.stay.application;

import lim.seyeon.safe.stay.domain.Area.Area;
import lim.seyeon.safe.stay.domain.Area.AreaRepository;
import lim.seyeon.safe.stay.presentation.DTO.AreaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {

    private AreaRepository areaRepository;
    private ValidationService validationService;

    @Autowired
    AreaService(AreaRepository areaRepository, ValidationService validationService) {
        this.areaRepository = areaRepository;
        this.validationService = validationService;
    }

    public AreaDTO add(AreaDTO areaDTO) {
        Area area = AreaDTO.toEntity(areaDTO);
        validationService.checkValid(areaDTO);

        Area savedArea = areaRepository.add(area);
        AreaDTO savedAreaDTO = AreaDTO.toDTO(savedArea);
        return savedAreaDTO;
    }

    public AreaDTO findAreaByAreaNum(Integer area_num) {
        Area area = areaRepository.findAreaByAreaNum(area_num);
        AreaDTO areaDTO = AreaDTO.toDTO(area);
        return areaDTO;
    }

    public List<AreaDTO> findAll() {
        List<Area> areas = areaRepository.findAllAreas();
        List<AreaDTO> areaDTOS = areas.stream()
                .map(area -> AreaDTO.toDTO(area))
                .toList();
        return areaDTOS;
    }

    public AreaDTO update(AreaDTO areaDTO) {
        Area area = AreaDTO.toEntity(areaDTO);
        Area updatedArea = areaRepository.update(area);
        AreaDTO updatedAreaDTO = AreaDTO.toDTO(updatedArea);
        return updatedAreaDTO;
    }

    public void delete(Integer areaNum) {
        areaRepository.delete(areaNum);
    }
}
