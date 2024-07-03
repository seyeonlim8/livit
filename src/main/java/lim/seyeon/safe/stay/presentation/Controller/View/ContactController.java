package lim.seyeon.safe.stay.presentation.Controller.View;

import lim.seyeon.safe.stay.application.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/contact")
public class ContactController {


    private UserDetailsService userDetailsService;
    private MailService mailService;

    @Autowired
    public ContactController(UserDetailsService userDetailsService, MailService mailService) {
        this.userDetailsService = userDetailsService;
        this.mailService = mailService;
    }

    @GetMapping
    public String contact(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);
        return "contact";
    }

    @PostMapping("/send")
    public String sendEmail(@RequestParam("name") String name,
                            @RequestParam("email") String userEmail,
                            @RequestParam("subject") String subject,
                            @RequestParam("content") String content,
                            Principal principal,
                            Model model) {
        String to = "livitstay@outlook.com";
        String fullContent = "Email from: " + name + "\nSender Email: " + userEmail + "\nSubject: " + subject + "\nContent: " + content;
        mailService.sendSimpleMessage(to, subject, fullContent);
        model.addAttribute("message", "Email sent!");
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);
        return "contact";
    }
}
