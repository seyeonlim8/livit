package lim.seyeon.safe.stay.application;

import lim.seyeon.safe.stay.domain.RoommatePreference.RoommatePreference;
import lim.seyeon.safe.stay.domain.RoommatePreference.RoommatePreferenceRepository;
import lim.seyeon.safe.stay.presentation.DTO.RoommatePreferenceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoommatePreferenceService {

    private RoommatePreferenceRepository roommatePreferenceRepository;
    private ValidationService validationService;

    @Autowired
    RoommatePreferenceService(RoommatePreferenceRepository roommatePreferenceRepository, ValidationService validationService) {
        this.roommatePreferenceRepository = roommatePreferenceRepository;
        this.validationService = validationService;
    }

    public RoommatePreferenceDTO add(RoommatePreferenceDTO roommatePreferenceDTO) {
        RoommatePreference roommatePreference = RoommatePreferenceDTO.toEntity(roommatePreferenceDTO);
        validationService.checkValid(roommatePreference);

        RoommatePreference savedRoommatePreference = roommatePreferenceRepository.add(roommatePreference);
        RoommatePreferenceDTO savedRoommatePreferenceDTO = RoommatePreferenceDTO.toDTO(savedRoommatePreference);
        return savedRoommatePreferenceDTO;
    }

    public RoommatePreferenceDTO findRoommatePreferenceByUserId(Long userId) {
        RoommatePreference roommatePreference = roommatePreferenceRepository.findRoommatePreferenceByUserId(userId);
        RoommatePreferenceDTO roommatePreferenceDTO = RoommatePreferenceDTO.toDTO(roommatePreference);
        return roommatePreferenceDTO;
    }

    public List<RoommatePreferenceDTO> findAllRoommatePreferences() {
        List<RoommatePreference> roommatePreferences = roommatePreferenceRepository.findAll();
        List<RoommatePreferenceDTO> roommatePreferenceDTOS = roommatePreferences.stream()
                .map(roommatePreference -> RoommatePreferenceDTO.toDTO(roommatePreference))
                .toList();
        return roommatePreferenceDTOS;
    }

    public RoommatePreferenceDTO update(RoommatePreferenceDTO roommatePreferenceDTO) {
        RoommatePreference roommatePreference = RoommatePreferenceDTO.toEntity(roommatePreferenceDTO);
        RoommatePreference updatedRoommatePreference = roommatePreferenceRepository.update(roommatePreference);
        RoommatePreferenceDTO updatedRoommatePreferenceDTO = RoommatePreferenceDTO.toDTO(updatedRoommatePreference);
        return updatedRoommatePreferenceDTO;
    }

    public void delete(Long userId) {
        roommatePreferenceRepository.delete(userId);
    }
}
