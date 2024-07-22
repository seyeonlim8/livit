package lim.seyeon.safe.stay.presentation.Controller.View;

import lim.seyeon.safe.stay.application.RoommatePreferenceService;
import lim.seyeon.safe.stay.application.UserDetailService;
import lim.seyeon.safe.stay.application.UserServiceImpl;
import lim.seyeon.safe.stay.presentation.DTO.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/roommate-search")
public class RoommateSearchController {

    private UserServiceImpl userServiceImpl;
    private RoommatePreferenceService roommatePreferenceService;
    private UserDetailService userDetailService;

    @Autowired
    RoommateSearchController(UserServiceImpl userServiceImpl, RoommatePreferenceService roommatePreferenceService, UserDetailService userDetailService) {
        this.userServiceImpl = userServiceImpl;
        this.roommatePreferenceService = roommatePreferenceService;
        this.userDetailService = userDetailService;
    }

    @GetMapping
    public String roommateSearch(@RequestParam(required = false) Integer bedtime,
                                 @RequestParam(required = false) Integer noise,
                                 @RequestParam(required = false) Integer cleanliness,
                                 @RequestParam(required = false) Integer visitors,
                                 @RequestParam(required = false) Integer smoking,
                                 @RequestParam(required = false) Integer drinking,
                                 @RequestParam(required = false) Integer pets,
                                 @RequestParam(required = false) Integer interaction,
                                 @RequestParam(required = false) Integer gender,
                                 @RequestParam(required = false) Integer culture,
                                 @RequestParam(required = false) Integer lang,
                                 @RequestParam(required = false) String sort,
                                 Model model,
                                 Principal principal) {

        String username = principal.getName();
        UserDTO userDTO = userServiceImpl.findUserByUsername(username);
        Long userId = userDTO.getId();
        model.addAttribute("userdetail", userDTO);
        model.addAttribute("userid", userId);

        boolean surveySubmitted = roommatePreferenceService.exists(userId);
        if (!surveySubmitted) {
            return "roommate-search";
        }

        RoommateFilter filter = new RoommateFilter();
        filter.setBedtime(bedtime);
        filter.setNoise(noise);
        filter.setCleanliness(cleanliness);
        filter.setVisitors(visitors);
        filter.setSmoking(smoking);
        filter.setDrinking(drinking);
        filter.setPets(pets);
        filter.setInteraction(interaction);
        filter.setGender(gender);
        filter.setCulture(culture);
        filter.setLang(lang);
        filter.setSort(sort);

        List<RoommatePreferenceDTO> roommates = roommatePreferenceService.findRoommates(filter, userId);
        Map<Long, UserDetailDTO> userDetails = new HashMap<>();
        Map<Long, Integer> matchRates = new HashMap<>();
        for(RoommatePreferenceDTO roommate : roommates) {
            // Remove original user from the list
            if(roommate.getUserId().equals(userId)) {
                roommates.remove(roommate);
                continue;
            }
            Integer matchRate = roommatePreferenceService.getMatchRate(userId, roommate.getUserId());
            if(matchRate == null) {
                matchRate = roommatePreferenceService.calculateAndAddMatchRate(userId, roommate.getUserId());
            }
            matchRates.put(roommate.getUserId(), matchRate);

            UserDetailDTO userDetailDTO = userDetailService.findUserDetailByUserId(roommate.getUserId());
            userDetails.put(roommate.getUserId(), userDetailDTO);
        }
        model.addAttribute("roommates", roommates);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("matchRates", matchRates);

        return "view-roommates";
    }

    @GetMapping("/{otherUserId}")
    public String viewHouse(@PathVariable Long otherUserId, Model model, Principal principal) {
        String username = principal.getName();
        UserDTO userDTO = userServiceImpl.findUserByUsername(username);
        model.addAttribute("userdetail", userDTO);

        UserDTO otherUserDTO = userServiceImpl.findUserById(otherUserId);
        model.addAttribute("otheruser", otherUserDTO);
        UserDetailDTO userDetailDTO = userDetailService.findUserDetailByUserId(otherUserId);
        model.addAttribute("userNameAndEmail", userDetailDTO);
        RoommatePreferenceDTO roommatePreferenceDTO = roommatePreferenceService.findRoommatePreferenceByUserId(otherUserId);
        model.addAttribute("roommatepreference", roommatePreferenceDTO);

        return "roommate-details";
    }

}
