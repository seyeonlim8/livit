package lim.seyeon.safe.stay;

import lim.seyeon.safe.stay.application.RoommatePreferenceService;
import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.presentation.DTO.RoommatePreferenceDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class RoommatePreferenceServiceTest {

    @Autowired
    private RoommatePreferenceService roommatePreferenceService;

    @Test
    @DisplayName("Retrieves the newly added roommate preference when user id is queried")
    void addAndFindRoommatePreferenceByUserIdTest() {
        RoommatePreferenceDTO roommatePreferenceDTO = new RoommatePreferenceDTO(
                1L, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
        );

        RoommatePreferenceDTO savedRoommatePreferenceDTO = roommatePreferenceService.add(roommatePreferenceDTO);
        RoommatePreferenceDTO foundRoommatePreferenceDTO = roommatePreferenceService.findRoommatePreferenceByUserId(1L);

        assertEquals(savedRoommatePreferenceDTO.getUserId(), foundRoommatePreferenceDTO.getUserId());
        assertEquals(savedRoommatePreferenceDTO.getBedtime(), foundRoommatePreferenceDTO.getBedtime());
        assertEquals(savedRoommatePreferenceDTO.getNoise(), foundRoommatePreferenceDTO.getNoise());
        assertEquals(savedRoommatePreferenceDTO.getCleanliness(), foundRoommatePreferenceDTO.getCleanliness());
        assertEquals(savedRoommatePreferenceDTO.getVisitors(), foundRoommatePreferenceDTO.getVisitors());
        assertEquals(savedRoommatePreferenceDTO.getSmoking(), foundRoommatePreferenceDTO.getSmoking());
        assertEquals(savedRoommatePreferenceDTO.getDrinking(), foundRoommatePreferenceDTO.getDrinking());
        assertEquals(savedRoommatePreferenceDTO.getPets(), foundRoommatePreferenceDTO.getPets());
        assertEquals(savedRoommatePreferenceDTO.getInteraction(), foundRoommatePreferenceDTO.getInteraction());
        assertEquals(savedRoommatePreferenceDTO.getGender(), foundRoommatePreferenceDTO.getGender());
        assertEquals(savedRoommatePreferenceDTO.getCulture(), foundRoommatePreferenceDTO.getCulture());
        assertEquals(savedRoommatePreferenceDTO.getLang(), foundRoommatePreferenceDTO.getLang());
    }

    @Test
    @DisplayName("Throws EntityNotFoundException when a non-existent user id is queried")
    void throwsEntityNotFoundExceptionTest() {
        Long notFoundId = -1L;
        assertThrows(EntityNotFoundException.class, () -> roommatePreferenceService.findRoommatePreferenceByUserId(notFoundId));
    }

    @Test
    @DisplayName("All roommate preferences should be retrieved")
    void findAllAreasTest() {
        RoommatePreferenceDTO roommatePreferenceDTO1 = new RoommatePreferenceDTO(1L, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        RoommatePreferenceDTO roommatePreferenceDTO2 = new RoommatePreferenceDTO(2L, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);

        roommatePreferenceService.add(roommatePreferenceDTO1);
        roommatePreferenceService.add(roommatePreferenceDTO2);
        List<RoommatePreferenceDTO> roommatePreferenceDTOS = roommatePreferenceService.findAllRoommatePreferences();

        assertEquals(2, roommatePreferenceDTOS.size());
    }

    @Test
    @DisplayName("Roommate Preference should be successfully updated")
    void updateRoommatePreferenceTest() {
        RoommatePreferenceDTO roommatePreferenceDTO = new RoommatePreferenceDTO(1L, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        RoommatePreferenceDTO addedRoommatePreference = roommatePreferenceService.add(roommatePreferenceDTO);

        roommatePreferenceDTO.setBedtime(2);
        RoommatePreferenceDTO updatedRoommatePreference = roommatePreferenceService.update(roommatePreferenceDTO);
        assertEquals(2, updatedRoommatePreference.getBedtime());
    }

    @Test
    @DisplayName("Roommate Preference should be successfully deleted")
    void deleteRoommatePreferenceTest() {
        RoommatePreferenceDTO roommatePreferenceDTO = new RoommatePreferenceDTO(1L, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        roommatePreferenceService.add(roommatePreferenceDTO);

        roommatePreferenceService.delete(roommatePreferenceDTO.getUserId());
        assertThrows(EntityNotFoundException.class, () -> roommatePreferenceService.findRoommatePreferenceByUserId(1L));
    }

    @Test
    @DisplayName("Match rate of two roommate preferences should be successfully calculated and displayed")
    void calculateAndAddMatchRateTest() {
        RoommatePreferenceDTO roommatePreferenceDTO1 = new RoommatePreferenceDTO(1L, 2, 1, 1, 1, 1, 2, 1, 0, 1, 1, 1);
        RoommatePreferenceDTO roommatePreferenceDTO2 = new RoommatePreferenceDTO(2L, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);

        roommatePreferenceService.add(roommatePreferenceDTO1);
        roommatePreferenceService.add(roommatePreferenceDTO2);
        roommatePreferenceService.calculateAndAddMatchRate(roommatePreferenceDTO1.getUserId(), roommatePreferenceDTO2.getUserId());
        System.out.println(roommatePreferenceService.getMatchRate(roommatePreferenceDTO1.getUserId(), roommatePreferenceDTO2.getUserId()));
        assertEquals(81, roommatePreferenceService.getMatchRate(roommatePreferenceDTO1.getUserId(), roommatePreferenceDTO2.getUserId()));
    }

    @Test
    @DisplayName("When given the matching question key and answer number, its text answer should be returned")
    void getQuestionAnswerTest() {
        String questionKey = "bedtime";
        int answerNum = 1;
        String answerText = roommatePreferenceService.getAnswerText(questionKey, answerNum);

        assertEquals("Early (Wake up before 7 AM, Sleep before 10 PM)", answerText);
    }
}
