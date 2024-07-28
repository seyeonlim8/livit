package lim.seyeon.safe.stay;

import lim.seyeon.safe.stay.application.CategoryService;
import lim.seyeon.safe.stay.application.PhotoService;
import lim.seyeon.safe.stay.application.PostService;
import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.domain.User.UserService;
import lim.seyeon.safe.stay.presentation.DTO.CategoryDTO;
import lim.seyeon.safe.stay.presentation.DTO.PhotoDTO;
import lim.seyeon.safe.stay.presentation.DTO.PostDTO;
import lim.seyeon.safe.stay.presentation.DTO.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class PhotoServiceTest {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Test
    @DisplayName("Retrieves the newly added photo when photo id is queried")
    void addAndFindPhotoByIdTest() {
        UserDTO userDTO = new UserDTO("username", "password");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO categoryDTO = new CategoryDTO("name", "description");
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);
        PhotoDTO photoDTO = new PhotoDTO("someURL", savedPostDTO.getId());

        PhotoDTO savedPhotoDTO = photoService.add(photoDTO);
        PhotoDTO foundPhotoDTO = photoService.findPhotoById(savedPhotoDTO.getId());

        assertEquals(savedPhotoDTO.getId(), foundPhotoDTO.getId());
        assertEquals(savedPhotoDTO.getUrl(), foundPhotoDTO.getUrl());
        assertEquals(savedPhotoDTO.getPostId(), foundPhotoDTO.getPostId());
    }

    @Test
    @DisplayName("Photos with a certain post id should be retrieved")
    void findPhotosByPostIdTest() {
        UserDTO userDTO = new UserDTO("username", "password");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO categoryDTO = new CategoryDTO("name", "description");
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO postDTO2 = new PostDTO("title2", "content2", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);
        PostDTO savedPostDTO2 = postService.add(postDTO2);
        PhotoDTO photoDTO = new PhotoDTO("someURL", savedPostDTO.getId());
        PhotoDTO photoDTO2 = new PhotoDTO("someURL2", savedPostDTO2.getId());

        photoService.add(photoDTO);
        photoService.add(photoDTO2);

        List<PhotoDTO> photoDTOS = photoService.findPhotosByPostId(savedPostDTO.getId());
        assertEquals(1, photoDTOS.size());
    }

    @Test
    @DisplayName("All photos should be retrieved")
    void findAllPhotos() {
        UserDTO userDTO = new UserDTO("username", "password");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO categoryDTO = new CategoryDTO("name", "description");
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);
        PhotoDTO photoDTO = new PhotoDTO("someURL", savedPostDTO.getId());
        PhotoDTO photoDTO2 = new PhotoDTO("someURL2", savedPostDTO.getId());

        photoService.add(photoDTO);
        photoService.add(photoDTO2);

        List<PhotoDTO> photoDTOS = photoService.findAllPhotos();
        assertEquals(2, photoDTOS.size());
    }

    @Test
    @DisplayName("Comment should be updated successfully")
    void updatePhotoTest() {
        UserDTO userDTO = new UserDTO("username", "password");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO categoryDTO = new CategoryDTO("name", "description");
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);
        PhotoDTO photoDTO = new PhotoDTO("someURL", savedPostDTO.getId());
        PhotoDTO savedPhotoDTO = photoService.add(photoDTO);

        savedPhotoDTO.setUrl("new URL");
        PhotoDTO updatedPhotoDTO = photoService.update(savedPhotoDTO);
        assertEquals("new URL", updatedPhotoDTO.getUrl());
    }

    @Test
    @DisplayName("Comment should be deleted successfully")
    void deletePhotoTest() {
        UserDTO userDTO = new UserDTO("username", "password");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO categoryDTO = new CategoryDTO("name", "description");
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);
        PhotoDTO photoDTO = new PhotoDTO("someURL", savedPostDTO.getId());
        PhotoDTO savedPhotoDTO = photoService.add(photoDTO);

        photoService.delete(savedPhotoDTO.getId());
        assertThrows(EntityNotFoundException.class, () -> photoService.findPhotoById(savedPhotoDTO.getId()));
    }
}
