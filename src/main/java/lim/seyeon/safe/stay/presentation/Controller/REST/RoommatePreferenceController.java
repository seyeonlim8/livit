package lim.seyeon.safe.stay.presentation.Controller.REST;

import jakarta.validation.Valid;
import lim.seyeon.safe.stay.application.RoommatePreferenceService;
import lim.seyeon.safe.stay.domain.RoommatePreference.RoommatePreference;
import lim.seyeon.safe.stay.presentation.DTO.RoommatePreferenceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roommate-preferences")
public class RoommatePreferenceController {

    public RoommatePreferenceService roommatePreferenceService;

    @Autowired
    public RoommatePreferenceController(RoommatePreferenceService roommatePreferenceService) {
        this.roommatePreferenceService = roommatePreferenceService;
    }

    @PostMapping
    public RoommatePreferenceDTO createRoommatePreference(@RequestBody RoommatePreferenceDTO roommatePreferenceDTO) {
        return roommatePreferenceService.add(roommatePreferenceDTO);
    }

    @GetMapping("/{userId}")
    public RoommatePreferenceDTO findRoommatePreferenceByUserId(@PathVariable Long userId) {
        return roommatePreferenceService.findRoommatePreferenceByUserId(userId);
    }

    @GetMapping
    public List<RoommatePreferenceDTO> findAllRoommatePreferences() {
        return roommatePreferenceService.findAllRoommatePreferences();
    }

    @PutMapping
    public RoommatePreferenceDTO updateRoommatePreference(@RequestBody RoommatePreferenceDTO roommatePreferenceDTO) {
        return roommatePreferenceService.update(roommatePreferenceDTO);
    }

    @DeleteMapping("/{userId}")
    public void deleteRoommatePreference(@PathVariable Long userId) {
        roommatePreferenceService.delete(userId);
    }

}
