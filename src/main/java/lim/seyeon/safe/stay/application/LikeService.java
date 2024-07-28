package lim.seyeon.safe.stay.application;

import lim.seyeon.safe.stay.domain.Category.Category;
import lim.seyeon.safe.stay.domain.Like.Like;
import lim.seyeon.safe.stay.domain.Like.LikeRepository;
import lim.seyeon.safe.stay.domain.Post.Post;
import lim.seyeon.safe.stay.domain.User.User;
import lim.seyeon.safe.stay.domain.User.UserService;
import lim.seyeon.safe.stay.presentation.DTO.CategoryDTO;
import lim.seyeon.safe.stay.presentation.DTO.LikeDTO;
import lim.seyeon.safe.stay.presentation.DTO.PostDTO;
import lim.seyeon.safe.stay.presentation.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    private LikeRepository likeRepository;
    private ValidationService validationService;
    private PostService postService;
    private UserService userService;
    private CategoryService categoryService;

    @Autowired
    public LikeService(LikeRepository likeRepository, ValidationService validationService, PostService postService, UserService userService, CategoryService categoryService, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.likeRepository = likeRepository;
        this.validationService = validationService;
        this.postService = postService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public LikeDTO add(LikeDTO likeDTO) {
        PostDTO postDTO = postService.findPostById(likeDTO.getPostId());
        UserDTO postUserDTO = userService.findUserById(postDTO.getUserId());
        User postUser = UserDTO.toEntity(postUserDTO);
        CategoryDTO categoryDTO = categoryService.findCategoryById(postDTO.getCategoryId());
        Category category = CategoryDTO.toEntity(categoryDTO);
        Post post = PostDTO.toEntity(postDTO, postUser, category);

        UserDTO likeUserDTO = userService.findUserById(likeDTO.getUserId());
        User likeUser = UserDTO.toEntity(likeUserDTO);

        Like like = LikeDTO.toEntity(likeDTO, likeUser, post);
        Like savedLike = likeRepository.add(like);
        LikeDTO savedLikeDTO = LikeDTO.toDTO(savedLike);
        return savedLikeDTO;
    }

    public LikeDTO findLikeById(Long id) {
        Like like = likeRepository.findLikeById(id);
        LikeDTO likeDTO = LikeDTO.toDTO(like);
        return likeDTO;
    }

    public List<LikeDTO> findLikesByUserId(Long userId) {
        List<Like> likes = likeRepository.findLikesByUserId(userId);
        List<LikeDTO> likeDTOS = likes.stream()
                .map(like -> LikeDTO.toDTO(like))
                .toList();
        return likeDTOS;
    }

    public List<LikeDTO> findLikesByPostId(Long postId) {
        List<Like> likes = likeRepository.findLikesByPostId(postId);
        List<LikeDTO> likeDTOS = likes.stream()
                .map(like -> LikeDTO.toDTO(like))
                .toList();
        return likeDTOS;
    }

    public List<LikeDTO> findAllLikes() {
        List<Like> likes = likeRepository.findAllLikes();
        List<LikeDTO> likeDTOS = likes.stream()
                .map(like -> LikeDTO.toDTO(like))
                .toList();
        return likeDTOS;
    }

    public LikeDTO update(LikeDTO likeDTO) {
        PostDTO postDTO = postService.findPostById(likeDTO.getPostId());
        UserDTO postUserDTO = userService.findUserById(postDTO.getUserId());
        User postUser = UserDTO.toEntity(postUserDTO);
        CategoryDTO categoryDTO = categoryService.findCategoryById(postDTO.getCategoryId());
        Category category = CategoryDTO.toEntity(categoryDTO);
        Post post = PostDTO.toEntity(postDTO, postUser, category);
        UserDTO likeUserDTO = userService.findUserById(likeDTO.getUserId());
        User likeUser = UserDTO.toEntity(likeUserDTO);

        Like like = LikeDTO.toEntity(likeDTO, likeUser, post);
        Like updatedLike = likeRepository.update(like);
        LikeDTO updatedLikeDTO = LikeDTO.toDTO(updatedLike);
        return updatedLikeDTO;
    }

    public void delete(Long id) {
        likeRepository.delete(id);
    }
}
