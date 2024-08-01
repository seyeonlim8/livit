package lim.seyeon.safe.stay.presentation.Controller.View;

import lim.seyeon.safe.stay.application.PostService;
import lim.seyeon.safe.stay.domain.User.User;
import lim.seyeon.safe.stay.domain.User.UserService;
import lim.seyeon.safe.stay.presentation.DTO.PostDTO;
import lim.seyeon.safe.stay.presentation.DTO.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/my-posts")
public class MyPostsController {

    private PostService postService;
    private UserService userService;

    public MyPostsController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    public String myPosts(Principal principal, Model model) {
        UserDTO userDTO = userService.findUserByUsername(principal.getName());
        List<PostDTO> myPosts = postService.findPostByUserId(userDTO.getId());
        model.addAttribute("myPosts", myPosts);

        return "my-posts";
    }

}
