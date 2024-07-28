package lim.seyeon.safe.stay.presentation.Controller.REST;

import lim.seyeon.safe.stay.application.PhotoService;
import lim.seyeon.safe.stay.presentation.DTO.PhotoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping
    public PhotoDTO create(@RequestBody PhotoDTO photoDTO) {
        return photoService.add(photoDTO);
    }

    @GetMapping(value = "/{id}")
    public PhotoDTO findPhotoById(@PathVariable Long id) {
        return photoService.findPhotoById(id);
    }

    @GetMapping
    public List<PhotoDTO> findPhotos(@RequestParam(required = false) Long postId) {
        if (postId == null) {
            return photoService.findAllPhotos();
        } else {
            return photoService.findPhotosByPostId(postId);
        }
    }

    @PutMapping(value = "/{id}")
    public PhotoDTO update(@PathVariable Long id, @RequestBody PhotoDTO photoDTO) {
        photoDTO.setId(id);
        return photoService.update(photoDTO);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        photoService.delete(id);
    }
}
