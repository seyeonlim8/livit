package lim.seyeon.safe.stay.presentation.Controller.REST;

import lim.seyeon.safe.stay.application.LikeService;
import lim.seyeon.safe.stay.presentation.DTO.LikeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public LikeDTO create(@RequestBody LikeDTO likeDTO) {
        return likeService.add(likeDTO);
    }

    @GetMapping(value = "/{id}")
    public LikeDTO findLikeById(@PathVariable Long id) {
        return likeService.findLikeById(id);
    }

    @GetMapping("/check")
    public ResponseEntity<LikeDTO> checkLike(@RequestParam Long userId, @RequestParam Long postId) {
        Optional<LikeDTO> like = Optional.ofNullable(likeService.findLikeByPostIdAndUserId(postId, userId));
        return like.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<LikeDTO> findLikes(@RequestParam(required = false) Long userId, @RequestParam(required = false) Long postId) {
        if (userId == null && postId == null) {
            return likeService.findAllLikes();
        } else if (userId != null) {
            return likeService.findLikesByUserId(userId);
        } else {
            return likeService.findLikesByPostId(postId);
        }
    }

    @PutMapping(value = "/{id}")
    public LikeDTO update(@PathVariable Long id, @RequestBody LikeDTO likeDTO) {
        likeDTO.setId(id);
        return likeService.update(likeDTO);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        likeService.delete(id);
    }
}