package lim.seyeon.safe.stay;

import lim.seyeon.safe.stay.application.CategoryService;
import lim.seyeon.safe.stay.application.LikeService;
import lim.seyeon.safe.stay.application.PostService;
import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.domain.User.UserService;
import lim.seyeon.safe.stay.presentation.DTO.CategoryDTO;
import lim.seyeon.safe.stay.presentation.DTO.LikeDTO;
import lim.seyeon.safe.stay.presentation.DTO.PostDTO;
import lim.seyeon.safe.stay.presentation.DTO.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class LikeServiceTest {

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @Test
    @DisplayName("Retrieves the newly added like when photo id is queried")
    void addAndFindLikeByIdTest() {
        UserDTO userDTO = new UserDTO("username", "password");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO categoryDTO = new CategoryDTO("name", "description");
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);

        UserDTO likedUserDTO = new UserDTO("likedUser", "password");
        UserDTO savedLikedUserDTO = userService.save(likedUserDTO);
        LikeDTO likeDTO = new LikeDTO(savedLikedUserDTO.getId(), savedPostDTO.getId());

        LikeDTO savedLikeDTO = likeService.add(likeDTO);
        LikeDTO foundLikeDTO = likeService.findLikeById(savedLikeDTO.getId());

        assertEquals(savedLikeDTO.getId(), foundLikeDTO.getId());
        assertEquals(savedLikeDTO.getUserId(), foundLikeDTO.getUserId());
        assertEquals(savedLikeDTO.getPostId(), foundLikeDTO.getPostId());
    }

    @Test
    @DisplayName("Throws EntityNotFoundException when a non-existent like id is queried")
    void throwsEntityNotFoundExceptionTest() {
        Long notFoundId = -1L;
        assertThrows(EntityNotFoundException.class, () -> likeService.findLikeById(notFoundId));
    }

    @Test
    @DisplayName("Likes with a certain user id should be retrieved")
    void findLikesByUserIdTest() {
        UserDTO userDTO = new UserDTO("username", "password");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO categoryDTO = new CategoryDTO("name", "description");
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);

        UserDTO likedUserDTO = new UserDTO("likedUser", "password");
        UserDTO savedLikedUserDTO = userService.save(likedUserDTO);
        UserDTO likedUserDTO2 = new UserDTO("likedUser2", "password");
        UserDTO savedLikedUserDTO2 = userService.save(likedUserDTO2);
        LikeDTO likeDTO = new LikeDTO(savedLikedUserDTO.getId(), savedPostDTO.getId());
        LikeDTO likeDTO2 = new LikeDTO(savedLikedUserDTO2.getId(), savedPostDTO.getId());

        likeService.add(likeDTO);
        likeService.add(likeDTO2);

        List<LikeDTO> likeDTOS = likeService.findLikesByUserId(savedLikedUserDTO.getId());
        assertEquals(1, likeDTOS.size());
    }

    @Test
    @DisplayName("Likes with a certain post id should be retrieved")
    void findLikesByPostIdTest() {
        UserDTO userDTO = new UserDTO("username", "password");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO categoryDTO = new CategoryDTO("name", "description");
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);
        PostDTO postDTO2 = new PostDTO("title2", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO2 = postService.add(postDTO2);

        UserDTO likedUserDTO = new UserDTO("likedUser", "password");
        UserDTO savedLikedUserDTO = userService.save(likedUserDTO);
        LikeDTO likeDTO = new LikeDTO(savedLikedUserDTO.getId(), savedPostDTO.getId());
        LikeDTO likeDTO2 = new LikeDTO(savedLikedUserDTO.getId(), savedPostDTO2.getId());

        likeService.add(likeDTO);
        likeService.add(likeDTO2);

        List<LikeDTO> likeDTOS = likeService.findLikesByPostId(savedPostDTO.getId());
        assertEquals(1, likeDTOS.size());
    }

    @Test
    @DisplayName("All likes should be retrieved")
    void findAllLikesTest() {
        UserDTO userDTO = new UserDTO("username", "password");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO categoryDTO = new CategoryDTO("name", "description");
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);
        PostDTO postDTO2 = new PostDTO("title2", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO2 = postService.add(postDTO2);

        UserDTO likedUserDTO = new UserDTO("likedUser", "password");
        UserDTO savedLikedUserDTO = userService.save(likedUserDTO);
        LikeDTO likeDTO = new LikeDTO(savedLikedUserDTO.getId(), savedPostDTO.getId());
        LikeDTO likeDTO2 = new LikeDTO(savedLikedUserDTO.getId(), savedPostDTO2.getId());

        likeService.add(likeDTO);
        likeService.add(likeDTO2);

        List<LikeDTO> likeDTOS = likeService.findAllLikes();
        assertEquals(2, likeDTOS.size());
    }

    @Test
    @DisplayName("Like should be updated successfully")
    void updatePhotoTest() {
        UserDTO userDTO = new UserDTO("username", "password");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO categoryDTO = new CategoryDTO("name", "description");
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);
        PostDTO postDTO2 = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO2 = postService.add(postDTO2);

        UserDTO likedUserDTO = new UserDTO("likedUser", "password");
        UserDTO savedLikedUserDTO = userService.save(likedUserDTO);
        LikeDTO likeDTO = new LikeDTO(savedLikedUserDTO.getId(), savedPostDTO.getId());
        LikeDTO savedLikeDTO = likeService.add(likeDTO);

        savedLikeDTO.setPostId(savedPostDTO2.getId());
        LikeDTO updatedLikeDTO = likeService.update(savedLikeDTO);
        assertEquals(savedPostDTO.getId(), updatedLikeDTO.getPostId());
    }

    @Test
    @DisplayName("Like should be deleted successfully")
    void deleteLikeTest() {
        UserDTO userDTO = new UserDTO("username", "password");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO categoryDTO = new CategoryDTO("name", "description");
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);

        UserDTO likedUserDTO = new UserDTO("likedUser", "password");
        UserDTO savedLikedUserDTO = userService.save(likedUserDTO);
        LikeDTO likeDTO = new LikeDTO(savedLikedUserDTO.getId(), savedPostDTO.getId());
        LikeDTO savedLikeDTO = likeService.add(likeDTO);

        likeService.delete(savedLikeDTO.getId());
        assertThrows(EntityNotFoundException.class, () -> likeService.findLikeById(savedLikeDTO.getId()));
    }
}
