package lim.seyeon.safe.stay.presentation.Controller;

import lim.seyeon.safe.stay.application.CategoryService;
import lim.seyeon.safe.stay.application.CommentService;
import lim.seyeon.safe.stay.application.PostService;
import lim.seyeon.safe.stay.presentation.DTO.CategoryDTO;
import lim.seyeon.safe.stay.presentation.DTO.CommentDTO;
import lim.seyeon.safe.stay.presentation.DTO.PostDTO;
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
@RequestMapping("/community")
public class CommunityController {

    private PostService postService;
    private CategoryService categoryService;
    private UserDetailsService userDetailsService;
    private CommentService commentService;

    @Autowired
    public CommunityController(PostService postService, CategoryService categoryService, UserDetailsService userDetailsService, CommentService commentService) {
        this.postService = postService;
        this.categoryService = categoryService;
        this.userDetailsService = userDetailsService;
        this.commentService = commentService;
    }

    @GetMapping
    public String viewCommunity(@RequestParam(required = false) Long categoryId, Principal principal, Model model) {
        List<PostDTO> posts;
        if (categoryId == null) {
            posts = postService.findAllPosts();
        } else {
            posts = postService.findPostByCategoryId(categoryId);
        }

        List<CategoryDTO> categories = categoryService.findAllCategories();

        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);
        model.addAttribute("posts", posts);
        model.addAttribute("categories", categories);

        return "community";
    }

    @GetMapping("/{postId}")
    public String viewPostDetails(@PathVariable Long postId, Principal principal, Model model) {
        PostDTO post = postService.findPostById(postId);
        List<CommentDTO> comments = commentService.findCommentsByPostId(postId);

        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);

        return "post-details";
    }
}

