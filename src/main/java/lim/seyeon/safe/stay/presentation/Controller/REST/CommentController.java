package lim.seyeon.safe.stay.presentation.Controller.REST;

import lim.seyeon.safe.stay.application.CommentService;
import lim.seyeon.safe.stay.presentation.DTO.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public CommentDTO create(@RequestBody CommentDTO commentDTO) {
        return commentService.add(commentDTO);
    }

    @GetMapping(value = "/{id}")
    public CommentDTO findComment(@PathVariable Long id) {
        return commentService.findCommentById(id);
    }

    @GetMapping
    public List<CommentDTO> findComments(@RequestParam(required = false) Long userId, @RequestParam(required = false) Long postId) {
        if(userId == null && postId == null) {
            return commentService.findAllComments();
        } else if (userId != null) {
            return commentService.findCommentsByUserId(userId);
        } else {
            return commentService.findCommentsByPostId(postId);
        }
    }

    @PutMapping(value = "/{id}")
    public CommentDTO update(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        commentDTO.setId(id);
        return commentService.update(commentDTO);
    }

    @DeleteMapping
    public void delete(@PathVariable Long id) {
        commentService.delete(id);
    }
}
