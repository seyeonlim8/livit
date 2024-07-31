package lim.seyeon.safe.stay.presentation.Controller.View;

import lim.seyeon.safe.stay.application.CategoryService;
import lim.seyeon.safe.stay.application.CommentService;
import lim.seyeon.safe.stay.application.LikeService;
import lim.seyeon.safe.stay.application.PostService;
import lim.seyeon.safe.stay.domain.User.UserService;
import lim.seyeon.safe.stay.presentation.DTO.*;
import lim.seyeon.safe.stay.presentation.Filter.PostFilter;
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
import java.util.*;

@Controller
@RequestMapping("/community")
public class CommunityController {

    private PostService postService;
    private CategoryService categoryService;
    private UserDetailsService userDetailsService;
    private CommentService commentService;
    private UserService userService;
    private LikeService likeService;

    @Autowired
    public CommunityController(PostService postService, CategoryService categoryService, UserDetailsService userDetailsService, CommentService commentService, UserService userService, LikeService likeService) {
        this.postService = postService;
        this.categoryService = categoryService;
        this.userDetailsService = userDetailsService;
        this.commentService = commentService;
        this.userService = userService;
        this.likeService = likeService;
    }

    @GetMapping
    public String viewCommunity(@RequestParam(required = false) Long categoryId,
                                @RequestParam(required = false) String sort,
                                Principal principal,
                                Model model) {
        PostFilter filter = new PostFilter();
        filter.setCategoryId(categoryId);
        filter.setSort(sort);

        List<PostDTO> posts = postService.findPosts(filter);
        model.addAttribute("posts", posts);

        List<CategoryDTO> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);

        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);

        Map<Long, String> postIdAndCategoryName = new HashMap<>();
        Map<Long, String> postIdAndUsername = new HashMap<>();
        Map<Long, Integer> postIdAndLikeCount = new HashMap<>();
        for (PostDTO post : posts) {
            postIdAndCategoryName.put(post.getId(), categoryService.findCategoryById(post.getCategoryId()).getName());
            postIdAndUsername.put(post.getId(), userService.findUserById(post.getUserId()).getUsername());
            postIdAndLikeCount.put(post.getId(), likeService.findLikesByPostId(post.getId()).size());
        }
        model.addAttribute("postIdAndCategoryName", postIdAndCategoryName);
        model.addAttribute("postIdAndUsername", postIdAndUsername);
        model.addAttribute("postIdAndLikeCount", postIdAndLikeCount);

        return "community";
    }

    @GetMapping("/{postId}")
    public String viewPostDetails(@PathVariable Long postId, Principal principal, Model model) {
        PostDTO post = postService.findPostById(postId);
        model.addAttribute("post", post);
        List<CommentDTO> comments = commentService.findCommentsByPostId(postId);
        model.addAttribute("comments", comments);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);

        Map<Long, String> commentIdAndCommenterUsername = new HashMap<>();
        for (CommentDTO commentDTO : comments) {
            String commenterName = userService.findUserById(commentDTO.getUserId()).getUsername();
            commentIdAndCommenterUsername.put(commentDTO.getId(), commenterName);
        }
        model.addAttribute("commentIdAndCommenterUsername", commentIdAndCommenterUsername);

        CategoryDTO categoryDTO = categoryService.findCategoryById(post.getCategoryId());
        model.addAttribute("categoryName", categoryDTO.getName());

        return "post-details";
    }

    @GetMapping("/new")
    public String addNewPost(Principal principal, Model model) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);

        UserDTO userDTO = userService.findUserByUsername(principal.getName());
        Long userId = userDTO.getId();
        model.addAttribute("userid", userId);

        return "create-post";
    }
}

