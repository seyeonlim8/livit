package lim.seyeon.safe.stay.presentation.Controller.View;

import lim.seyeon.safe.stay.application.UserServiceImpl;
import lim.seyeon.safe.stay.presentation.DTO.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/roommate-search")
public class RoommateSearchController {

    private static final Logger logger = LoggerFactory.getLogger(RoommateSearchController.class);

    private UserServiceImpl userServiceImpl;

    @Autowired
    RoommateSearchController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping
    public String roommateSearch(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            UserDTO userDTO = userServiceImpl.findUserByUsername(username);
            model.addAttribute("userdetail", userDTO);
            model.addAttribute("userid", userDTO.getId());
        } else {
            logger.debug("Principal is null");
        }
        return "roommate-search";
    }
}
