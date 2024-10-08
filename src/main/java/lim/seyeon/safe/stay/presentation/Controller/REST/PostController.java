package lim.seyeon.safe.stay.presentation.Controller.REST;

import jakarta.validation.Valid;
import lim.seyeon.safe.stay.application.PhotoService;
import lim.seyeon.safe.stay.application.PostService;
import lim.seyeon.safe.stay.application.S3Service;
import lim.seyeon.safe.stay.presentation.DTO.PhotoDTO;
import lim.seyeon.safe.stay.presentation.DTO.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;
    private PhotoService photoService;
    private S3Service s3Service;

    @Autowired
    public PostController(PostService postService, PhotoService photoService, S3Service s3Service) {
        this.postService = postService;
        this.photoService = photoService;
        this.s3Service = s3Service;
    }

    @PostMapping
    public PostDTO create(@RequestPart("post") PostDTO postDTO, @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {
        // 1. Save the post to get the generated post ID
        PostDTO savedPost = postService.add(postDTO);

        // 2. Save the photos using the generated post ID
        List<PhotoDTO> photos = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = s3Service.uploadFile(file);
            PhotoDTO photoDTO = new PhotoDTO(s3Service.getFileUrl(fileName), savedPost.getId());
            PhotoDTO savedPhoto = photoService.add(photoDTO);
            photos.add(savedPhoto);
        }

        // 3. Update the saved post with the list of photos
        savedPost.setPhotos(photos);

        return savedPost;
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
