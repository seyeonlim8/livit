package lim.seyeon.safe.stay;

import lim.seyeon.safe.stay.application.CategoryService;
import lim.seyeon.safe.stay.application.CommentService;
import lim.seyeon.safe.stay.application.PostService;
import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.domain.User.UserService;
import lim.seyeon.safe.stay.presentation.DTO.CategoryDTO;
import lim.seyeon.safe.stay.presentation.DTO.CommentDTO;
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
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @Test
    @DisplayName("Retrieves the newly added comment when comment id is queried")
    void addAndFindCommentByIdTest() {
        UserDTO userDTO = new UserDTO( "username", "password");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO categoryDTO = new CategoryDTO( "name", "description");
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);
        CommentDTO commentDTO = new CommentDTO("content", LocalDateTime.now(), savedUserDTO.getId(), savedPostDTO.getId());

        CommentDTO savedCommentDTO = commentService.add(commentDTO);
        CommentDTO foundCommentDTO = commentService.findCommentById(savedCommentDTO.getId());

        assertEquals(savedCommentDTO.getId(), foundCommentDTO.getId());
        assertEquals(savedCommentDTO.getContent(), foundCommentDTO.getContent());
        Duration durationCreated = Duration.between(savedCommentDTO.getCreatedAt(), foundCommentDTO.getCreatedAt());
        assertTrue(Math.abs(durationCreated.getSeconds()) <= 1, "createdAt timestamps differ by more than 1 second");
        assertEquals(savedCommentDTO.getUserId(), foundCommentDTO.getUserId());
        assertEquals(savedCommentDTO.getPostId(), foundCommentDTO.getPostId());
    }

    @Test
    @DisplayName("Throws EntityNotFoundException when a non-existent comment id is queried")
    void throwsEntityNotFoundExceptionTest() {
        Long notFoundId = -1L;
        assertThrows(EntityNotFoundException.class, () -> commentService.findCommentById(notFoundId));
    }

    @Test
    @DisplayName("Comment with a certain user id should be retrieved")
    void findCommentByUserIdTest() {
        UserDTO userDTO = new UserDTO( "username", "password");
        UserDTO userDTO2 = new UserDTO( "username2", "password");
        UserDTO savedUserDTO = userService.save(userDTO);
        UserDTO savedUserDTO2 = userService.save(userDTO2);
        CategoryDTO categoryDTO = new CategoryDTO( "name", "description");
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);
        CommentDTO commentDTO = new CommentDTO("content", LocalDateTime.now(), savedUserDTO.getId(), savedPostDTO.getId());
        CommentDTO commentDTO2 = new CommentDTO("content2", LocalDateTime.now(), savedUserDTO2.getId(), savedPostDTO.getId());

        commentService.add(commentDTO);
        commentService.add(commentDTO2);

        List<CommentDTO> comments = commentService.findCommentsByUserId(savedUserDTO.getId());
        assertEquals(1, comments.size());
    }

    @Test
    @DisplayName("Comment with a certain user id should be retrieved")
    void findCommentByPostIdTest() {
        UserDTO userDTO = new UserDTO( "username", "password");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO categoryDTO = new CategoryDTO( "name", "description");
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO postDTO2 = new PostDTO("title2", "content2", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);
        PostDTO savedPostDTO2 = postService.add(postDTO2);
        CommentDTO commentDTO = new CommentDTO("content", LocalDateTime.now(), savedUserDTO.getId(), savedPostDTO.getId());
        CommentDTO commentDTO2 = new CommentDTO("content2", LocalDateTime.now(), savedUserDTO.getId(), savedPostDTO2.getId());

        commentService.add(commentDTO);
        commentService.add(commentDTO2);

        List<CommentDTO> comments = commentService.findCommentsByPostId(savedPostDTO.getId());
        assertEquals(1, comments.size());
    }

    @Test
    @DisplayName("All comments should be retrieved")
    void findAllCommentsTest() {
        UserDTO userDTO = new UserDTO( "username", "password");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO categoryDTO = new CategoryDTO( "name", "description");
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);
        CommentDTO commentDTO = new CommentDTO("content", LocalDateTime.now(), savedUserDTO.getId(), savedPostDTO.getId());
        CommentDTO commentDTO2 = new CommentDTO("content2", LocalDateTime.now(), savedUserDTO.getId(), savedPostDTO.getId());

        commentService.add(commentDTO);
        commentService.add(commentDTO2);

        List<CommentDTO> comments = commentService.findAllComments();
        assertEquals(2, comments.size());
    }

    @Test
    @DisplayName("Comment should be updated successfully")
    void updateCommentTest() {
        UserDTO userDTO = new UserDTO( "username", "password");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO categoryDTO = new CategoryDTO( "name", "description");
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);
        CommentDTO commentDTO = new CommentDTO("content", LocalDateTime.now(), savedUserDTO.getId(), savedPostDTO.getId());
        CommentDTO savedCommentDTO = commentService.add(commentDTO);

        savedCommentDTO.setContent("new content");
        CommentDTO updatedCommentDTO = commentService.update(savedCommentDTO);
        assertEquals("new content", updatedCommentDTO.getContent());
    }

    @Test
    @DisplayName("Comment should be deleted successfully")
    void deleteCommentTest() {
        UserDTO userDTO = new UserDTO( "username", "password");
        UserDTO savedUserDTO = userService.save(userDTO);
        CategoryDTO categoryDTO = new CategoryDTO( "name", "description");
        CategoryDTO savedCategoryDTO = categoryService.add(categoryDTO);
        PostDTO postDTO = new PostDTO("title", "content", LocalDateTime.now(), LocalDateTime.now(), savedUserDTO.getId(), savedCategoryDTO.getId());
        PostDTO savedPostDTO = postService.add(postDTO);
        CommentDTO commentDTO = new CommentDTO("content", LocalDateTime.now(), savedUserDTO.getId(), savedPostDTO.getId());
        CommentDTO savedCommentDTO = commentService.add(commentDTO);

        commentService.delete(savedCommentDTO.getId());
        assertThrows(EntityNotFoundException.class, () -> commentService.findCommentById(savedCommentDTO.getId()));
    }
}
