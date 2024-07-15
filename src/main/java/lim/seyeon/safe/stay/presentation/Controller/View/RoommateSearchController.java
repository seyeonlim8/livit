package lim.seyeon.safe.stay.presentation.Controller.View;

import lim.seyeon.safe.stay.application.RoommatePreferenceService;
import lim.seyeon.safe.stay.application.UserServiceImpl;
import lim.seyeon.safe.stay.presentation.DTO.HouseDTO;
import lim.seyeon.safe.stay.presentation.DTO.RoommateFilter;
import lim.seyeon.safe.stay.presentation.DTO.RoommatePreferenceDTO;
import lim.seyeon.safe.stay.presentation.DTO.UserDTO;
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
import java.util.List;

@Controller
@RequestMapping("/roommate-search")
public class RoommateSearchController {

    private UserServiceImpl userServiceImpl;
    private RoommatePreferenceService roommatePreferenceService;

    @Autowired
    RoommateSearchController(UserServiceImpl userServiceImpl, RoommatePreferenceService roommatePreferenceService) {
        this.userServiceImpl = userServiceImpl;
        this.roommatePreferenceService = roommatePreferenceService;
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

        List<RoommatePreferenceDTO> roommates = roommatePreferenceService.findRoommates(filter);
        model.addAttribute("roommates", roommates);

        return "view-roommates";
    }

    @GetMapping("/{otherUserId}")
    public String viewHouse(@PathVariable Long otherUserId, Model model, Principal principal) {
        String username = principal.getName();
        UserDTO userDTO = userServiceImpl.findUserByUsername(username);
        model.addAttribute("userdetail", userDTO);

        String otherUsername = userServiceImpl.findUserById(otherUserId).getUsername();
        model.addAttribute("otherusername", otherUsername);
        RoommatePreferenceDTO roommatePreferenceDTO = roommatePreferenceService.findRoommatePreferenceByUserId(otherUserId);
        model.addAttribute("roommatepreference", roommatePreferenceDTO);

        return "roommate-details";
    }

}
