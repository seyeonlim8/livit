package lim.seyeon.safe.stay.presentation.Controller.REST;

import jakarta.validation.Valid;
import lim.seyeon.safe.stay.application.PostService;
import lim.seyeon.safe.stay.presentation.DTO.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public PostDTO create(@Valid @RequestBody PostDTO postDTO) {
        return postService.add(postDTO);
    }

    @GetMapping(value = "/{id}")
    public PostDTO findPostById(@PathVariable Long id) {
        return postService.findPostById(id);
    }

    @GetMapping
    public List<PostDTO> findPosts(@RequestParam(required = false) Long userId, @RequestParam(required = false) Long categoryId) {
        if(userId == null && categoryId == null) {
            return postService.findAllPosts();
        } else if(userId != null) {
            return postService.findPostByUserId(userId);
        } else {
            return postService.findPostByCategoryId(categoryId);
        }
    }

    @PutMapping(value = "/{id}")
    public PostDTO update(@PathVariable Long id, @Valid @RequestBody PostDTO postDTO) {
        postDTO.setId(id);
        return postService.update(postDTO);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        postService.delete(id);
    }
}
