package lim.seyeon.safe.stay.application;

import lim.seyeon.safe.stay.domain.RoommatePreference.RoommatePreference;
import lim.seyeon.safe.stay.domain.RoommatePreference.RoommatePreferenceRepository;
import lim.seyeon.safe.stay.presentation.DTO.RoommateFilter;
import lim.seyeon.safe.stay.presentation.DTO.RoommatePreferenceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

    public boolean exists(Long userId) {
        return roommatePreferenceRepository.exists(userId);
    }

    public List<RoommatePreferenceDTO> findRoommates(RoommateFilter filter, Long currentUserId) {
        List<RoommatePreference> roommatePreferences = roommatePreferenceRepository.findRoommates(filter, currentUserId);
        List<RoommatePreferenceDTO> roommatePreferenceDTOS = roommatePreferences.stream()
                .map(roommatePreference -> RoommatePreferenceDTO.toDTO(roommatePreference))
                .toList();
        List<RoommatePreferenceDTO> mutableRoommatePreferenceDTOS = new ArrayList<>(roommatePreferenceDTOS);
        return sort(mutableRoommatePreferenceDTOS, currentUserId, filter.getSort());
    }

    public List<RoommatePreferenceDTO> sort(List<RoommatePreferenceDTO> roommatePreferenceDTOS, Long userId, String sort) {
        if("matchRateLowHigh".equals(sort)) {
            roommatePreferenceDTOS.sort(Comparator.comparing(roommatePreferenceDTO -> getMatchRate(userId, roommatePreferenceDTO.getUserId())));
        } else if("matchRateHighLow".equals(sort)) {
            Comparator<RoommatePreferenceDTO> comparator = Comparator.comparing(roommatePreferenceDTO -> getMatchRate(userId, roommatePreferenceDTO.getUserId()));
            roommatePreferenceDTOS.sort(comparator.reversed());
        }
        return roommatePreferenceDTOS;
    }

    public Integer getMatchRate(Long user_id1, Long user_id2) {
        return roommatePreferenceRepository.getMatchRate(user_id1, user_id2);
    }

    public Integer calculateAndAddMatchRate(Long user_id1, Long user_id2) {
        return roommatePreferenceRepository.addMatchRate(user_id1, user_id2);
    }

    public String getAnswerText(String questionKey, int answerNum) {
        return roommatePreferenceRepository.getAnswerText(questionKey, answerNum);
    }

    public List<String> getAnswerTexts(RoommatePreferenceDTO roommatePreferenceDTO) {
        String bedtime = "Bedtime: " + roommatePreferenceRepository.getAnswerText("bedtime", roommatePreferenceDTO.getBedtime());
        String noise = "Noise: " + roommatePreferenceRepository.getAnswerText("noise", roommatePreferenceDTO.getNoise());
        String cleanliness = "Cleanliness: " + roommatePreferenceRepository.getAnswerText("cleanliness", roommatePreferenceDTO.getCleanliness());
        String visitors = "Visitors: " + roommatePreferenceRepository.getAnswerText("visitors", roommatePreferenceDTO.getVisitors());
        String smoking = "Smoking: " + roommatePreferenceRepository.getAnswerText("smoking", roommatePreferenceDTO.getSmoking());
        String drinking = "Drinking: " + roommatePreferenceRepository.getAnswerText("drinking", roommatePreferenceDTO.getDrinking());
        String pets = "Pets: " + roommatePreferenceRepository.getAnswerText("pets", roommatePreferenceDTO.getPets());
        String interaction = "Interaction with roommate: " + roommatePreferenceRepository.getAnswerText("interaction", roommatePreferenceDTO.getInteraction());
        String gender = "Preferred Gender: " + roommatePreferenceRepository.getAnswerText("gender", roommatePreferenceDTO.getGender());
        String culture = "Preferred Culture: " + roommatePreferenceRepository.getAnswerText("culture", roommatePreferenceDTO.getCulture());
        String lang = "Preferred Language spoken at home: " + roommatePreferenceRepository.getAnswerText("lang", roommatePreferenceDTO.getLang());

        List<String> answerTexts
                = new ArrayList<>(Arrays.asList(bedtime, noise, cleanliness, visitors, smoking, drinking, pets, interaction, gender, culture, lang));
        return answerTexts;
    }
}
