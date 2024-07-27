package lim.seyeon.safe.stay;

import lim.seyeon.safe.stay.application.CategoryService;
import lim.seyeon.safe.stay.application.PostService;
import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.domain.User.UserService;
import lim.seyeon.safe.stay.presentation.DTO.CategoryDTO;
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
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Test
    @DisplayName("Retrieves the newly added post when post id is queried")
    void addAndFindPostByIdTest() {
        UserDTO userDTO = new UserDTO( "username", "password");
        CategoryDTO categoryDTO = new CategoryDTO( "name", "description");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());

        PostDTO savedPostDTO = postService.add(postDTO);
        PostDTO foundPostDTO = postService.findPostById(savedPostDTO.getId());

        assertEquals(savedPostDTO.getId(), foundPostDTO.getId());
        assertEquals(savedPostDTO.getTitle(), foundPostDTO.getTitle());
        assertEquals(savedPostDTO.getContent(), foundPostDTO.getContent());

        Duration durationCreated = Duration.between(savedPostDTO.getCreatedAt(), foundPostDTO.getCreatedAt());
        assertTrue(Math.abs(durationCreated.getSeconds()) <= 1, "createdAt timestamps differ by more than 1 second");

        Duration durationUpdated = Duration.between(savedPostDTO.getUpdatedAt(), foundPostDTO.getUpdatedAt());
        assertTrue(Math.abs(durationUpdated.getSeconds()) <= 1, "updatedAt timestamps differ by more than 1 second");

        assertEquals(savedPostDTO.getUserId(), foundPostDTO.getUserId());
        assertEquals(savedPostDTO.getCategoryId(), foundPostDTO.getCategoryId());
    }

    @Test
    @DisplayName("Throws EntityNotFoundException when a non-existent post id is queried")
    void throwsEntityNotFoundExceptionTest() {
        Long notFoundId = -1L;
        assertThrows(EntityNotFoundException.class, () -> postService.findPostById(notFoundId));
    }

    @Test
    @DisplayName("All posts should be retrieved")
    void findAllPostsTest() {
        UserDTO userDTO = new UserDTO( "username", "password");
        CategoryDTO categoryDTO = new CategoryDTO( "name", "description");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);

        PostDTO postDTO1 = new PostDTO("title1", "content1", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO postDTO2 = new PostDTO("title2", "content2", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        postService.add(postDTO1);
        postService.add(postDTO2);

        List<PostDTO> postDTOList = postService.findAllPosts();
        assertEquals(2, postDTOList.size());
    }

    @Test
    @DisplayName("Post should be updated successfully")
    void updatePostTest() {
        UserDTO userDTO = new UserDTO( "username", "password");
        CategoryDTO categoryDTO = new CategoryDTO( "name", "description");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);

        savedPostDTO.setTitle("updatedTitle");
        PostDTO updatedPostDTO = postService.update(savedPostDTO);
        assertEquals("updatedTitle", updatedPostDTO.getTitle());
    }

    @Test
    @DisplayName("Post should be deleted successfully")
    void deletePostTest() {
        UserDTO userDTO = new UserDTO( "username", "password");
        CategoryDTO categoryDTO = new CategoryDTO( "name", "description");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);

        postService.delete(savedPostDTO.getId());
        assertThrows(EntityNotFoundException.class, () -> postService.findPostById(savedPostDTO.getId()));
    }

}
