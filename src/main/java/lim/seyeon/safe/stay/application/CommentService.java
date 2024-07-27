package lim.seyeon.safe.stay.application;

import lim.seyeon.safe.stay.domain.Category.Category;
import lim.seyeon.safe.stay.domain.Comment.Comment;
import lim.seyeon.safe.stay.domain.Comment.CommentRepository;
import lim.seyeon.safe.stay.domain.Post.Post;
import lim.seyeon.safe.stay.domain.User.User;
import lim.seyeon.safe.stay.domain.User.UserService;
import lim.seyeon.safe.stay.presentation.DTO.CategoryDTO;
import lim.seyeon.safe.stay.presentation.DTO.CommentDTO;
import lim.seyeon.safe.stay.presentation.DTO.PostDTO;
import lim.seyeon.safe.stay.presentation.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private ValidationService validationService;
    private UserService userService;
    private PostService postService;
    private CategoryService categoryService;

    @Autowired
    public CommentService(CommentRepository commentRepository, ValidationService validationService, UserService userService, PostService postService, CategoryService categoryService) {
        this.commentRepository = commentRepository;
        this.validationService = validationService;
        this.userService = userService;
        this.postService = postService;
        this.categoryService = categoryService;
    }

    public CommentDTO add(CommentDTO commentDTO) {
        UserDTO userDTO = userService.findUserById(commentDTO.getUserId());
        User user = UserDTO.toEntity(userDTO);
        PostDTO postDTO = postService.findPostById(commentDTO.getPostId());
        CategoryDTO categoryDTO = categoryService.findCategoryById(postDTO.getCategoryId());
        Category category = CategoryDTO.toEntity(categoryDTO);
        Post post = PostDTO.toEntity(postDTO, user, category);
        Comment comment = CommentDTO.toEntity(commentDTO, user, post);
        validationService.checkValid(comment);

        Comment savedComment = commentRepository.add(comment);
        CommentDTO savedCommentDTO = CommentDTO.toDTO(savedComment);
        return savedCommentDTO;
    }

    public CommentDTO findCommentById(Long id) {
        Comment comment = commentRepository.findCommentById(id);
        CommentDTO commentDTO = CommentDTO.toDTO(comment);
        return commentDTO;
    }

    public List<CommentDTO> findCommentsByUserId(Long userId) {
        List<Comment> comments = commentRepository.findCommentsByUserId(userId);
        List<CommentDTO> commentDTOS = comments.stream()
                .map(comment -> CommentDTO.toDTO(comment))
                .toList();
        return commentDTOS;
    }

    public List<CommentDTO> findCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findCommentsByPostId(postId);
        List<CommentDTO> commentDTOS = comments.stream()
                .map(comment -> CommentDTO.toDTO(comment))
                .toList();
        return commentDTOS;
    }

    public List<CommentDTO> findAllComments() {
        List<Comment> comments = commentRepository.findAllComments();
        List<CommentDTO> commentDTOS = comments.stream()
                .map(comment -> CommentDTO.toDTO(comment))
                .toList();
        return commentDTOS;
    }

    public CommentDTO update(CommentDTO commentDTO) {
        UserDTO userDTO = userService.findUserById(commentDTO.getUserId());
        User user = UserDTO.toEntity(userDTO);
        PostDTO postDTO = postService.findPostById(commentDTO.getPostId());
        CategoryDTO categoryDTO = categoryService.findCategoryById(postDTO.getCategoryId());
        Category category = CategoryDTO.toEntity(categoryDTO);
        Post post = PostDTO.toEntity(postDTO, user, category);
        Comment comment = CommentDTO.toEntity(commentDTO, user, post);

        Comment updatedComment = commentRepository.update(comment);
        CommentDTO updatedCommentDTO = CommentDTO.toDTO(updatedComment);
        return updatedCommentDTO;
    }

    public void delete(Long id) {
        commentRepository.delete(id);
    }
}
