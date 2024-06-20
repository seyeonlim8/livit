package lim.seyeon.safe.stay.presentation.Controller;

import lim.seyeon.safe.stay.presentation.DTO.UserDTO;
import org.springframework.ui.Model;
import lim.seyeon.safe.stay.domain.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, UserDTO userDTO) {
        model.addAttribute("userDTO", userDTO);
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model, UserDTO userDTO) {
        model.addAttribute("userDTO", userDTO);
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerSavable(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        UserDTO existingUserDTO = userService.findUserByUsername(userDTO.getUsername());
        if(existingUserDTO != null) {
            model.addAttribute("userDTO", userDTO);
            return "register";
        }
        userService.save(userDTO);
        return "redirect:/home";
    }
}
