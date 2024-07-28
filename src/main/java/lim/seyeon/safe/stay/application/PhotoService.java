package lim.seyeon.safe.stay.application;

import lim.seyeon.safe.stay.domain.Category.Category;
import lim.seyeon.safe.stay.domain.Photo.Photo;
import lim.seyeon.safe.stay.domain.Photo.PhotoRepository;
import lim.seyeon.safe.stay.domain.Post.Post;
import lim.seyeon.safe.stay.domain.User.User;
import lim.seyeon.safe.stay.domain.User.UserService;
import lim.seyeon.safe.stay.presentation.DTO.CategoryDTO;
import lim.seyeon.safe.stay.presentation.DTO.PhotoDTO;
import lim.seyeon.safe.stay.presentation.DTO.PostDTO;
import lim.seyeon.safe.stay.presentation.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

    private PhotoRepository photoRepository;
    private ValidationService validationService;
    private PostService postService;
    private CategoryService categoryService;
    private UserService userService;

    @Autowired
    public PhotoService(PhotoRepository photoRepository, ValidationService validationService, PostService postService, CategoryService categoryService, UserService userService) {
        this.photoRepository = photoRepository;
        this.validationService = validationService;
        this.postService = postService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    public PhotoDTO add(PhotoDTO photoDTO) {
        PostDTO postDTO = postService.findPostById(photoDTO.getPostId());
        CategoryDTO categoryDTO = categoryService.findCategoryById(postDTO.getCategoryId());
        Category category = CategoryDTO.toEntity(categoryDTO);
        UserDTO userDTO = userService.findUserById(postDTO.getUserId());
        User user = UserDTO.toEntity(userDTO);
        Post post = PostDTO.toEntity(postDTO, user, category);
        Photo photo = PhotoDTO.toEntity(photoDTO, post);
        validationService.checkValid(photo);

        Photo savedPhoto = photoRepository.add(photo);
        PhotoDTO savedPhotoDTO = PhotoDTO.toDTO(savedPhoto);
        return savedPhotoDTO;
    }

    public PhotoDTO findPhotoById(Long id) {
        Photo photo = photoRepository.findPhotoById(id);
        PhotoDTO photoDTO = PhotoDTO.toDTO(photo);
        return photoDTO;
    }

    public List<PhotoDTO> findPhotosByPostId(Long postId) {
        List<Photo> photos = photoRepository.findPhotosByPostId(postId);
        List<PhotoDTO> photoDTOS = photos.stream()
                .map(photo -> PhotoDTO.toDTO(photo))
                .toList();
        return photoDTOS;
    }

    public List<PhotoDTO> findAllPhotos() {
        List<Photo> photos = photoRepository.findAllPhotos();
        List<PhotoDTO> photoDTOS = photos.stream()
                .map(photo -> PhotoDTO.toDTO(photo))
                .toList();
        return photoDTOS;
    }

    public PhotoDTO update(PhotoDTO photoDTO) {
        PostDTO postDTO = postService.findPostById(photoDTO.getPostId());
        CategoryDTO categoryDTO = categoryService.findCategoryById(postDTO.getCategoryId());
        Category category = CategoryDTO.toEntity(categoryDTO);
        UserDTO userDTO = userService.findUserById(postDTO.getUserId());
        User user = UserDTO.toEntity(userDTO);
        Post post = PostDTO.toEntity(postDTO, user, category);

        Photo photo = PhotoDTO.toEntity(photoDTO, post);
        Photo updatedPhoto = photoRepository.update(photo);
        PhotoDTO updatedPhotoDTO = PhotoDTO.toDTO(updatedPhoto);
        return updatedPhotoDTO;
    }

    public void delete(Long id) {
        photoRepository.delete(id);
    }
}
