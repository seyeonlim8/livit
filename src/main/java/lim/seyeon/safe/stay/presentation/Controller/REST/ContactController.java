package lim.seyeon.safe.stay.presentation.Controller.REST;

import lim.seyeon.safe.stay.domain.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class ContactController {

    @Autowired
    private UserDetailsService userDetailsService;

    private UserService userService;

    @GetMapping("/contact")
    public String contact(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);
        return "contact";
    }

    @PostMapping("/send-message")
    public String sendMessage(@RequestParam String name, @RequestParam String email, @RequestParam String message, Model model) {
        // Process the message (e.g., save to database, send an email, etc.)

        // Add a success message to the model
        model.addAttribute("successMessage", "Your message has been sent successfully!");

        // Redirect back to the contact page or another page
        return "redirect:/contact";
    }
}
