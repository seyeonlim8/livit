package lim.seyeon.safe.stay.presentation.Controller.View;

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
@RequestMapping("/how-it-works")
public class HowItWorksController {

    private static final Logger logger = LoggerFactory.getLogger(ViewHousesController.class);

    private UserDetailsService userDetailsService;

    @Autowired
    public HowItWorksController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public String howItWorks(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            model.addAttribute("userdetail", userDetails);
        } else {
            logger.debug("Principal is null");
        }
        return "how-it-works";
    }
}
