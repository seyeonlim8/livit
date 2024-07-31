package lim.seyeon.safe.stay.application;

import lim.seyeon.safe.stay.domain.Category.Category;
import lim.seyeon.safe.stay.domain.Like.Like;
import lim.seyeon.safe.stay.domain.Post.Post;
import lim.seyeon.safe.stay.domain.Post.PostRepository;
import lim.seyeon.safe.stay.domain.User.User;
import lim.seyeon.safe.stay.domain.User.UserService;
import lim.seyeon.safe.stay.presentation.DTO.CategoryDTO;
import lim.seyeon.safe.stay.presentation.DTO.LikeDTO;
import lim.seyeon.safe.stay.presentation.DTO.PostDTO;
import lim.seyeon.safe.stay.presentation.DTO.UserDTO;
import lim.seyeon.safe.stay.presentation.Filter.PostFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    private ValidationService validationService;
    private UserService userService;
    private CategoryService categoryService;

    @Autowired
    public PostService(PostRepository postRepository, ValidationService validationService, UserServiceImpl userService, CategoryService categoryService) {
        this.postRepository = postRepository;
        this.validationService = validationService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    public PostDTO add(PostDTO postDTO) {
        UserDTO userDTO = userService.findUserById(postDTO.getUserId());
        User user = UserDTO.toEntity(userDTO);
        CategoryDTO categoryDTO = categoryService.findCategoryById(postDTO.getCategoryId());
        Category category = CategoryDTO.toEntity(categoryDTO);
        Post post = PostDTO.toEntity(postDTO, user, category);
        validationService.checkValid(post);

        Post savedPost = postRepository.add(post);
        PostDTO savedPostDTO = PostDTO.toDTO(savedPost);
        return savedPostDTO;
    }

    public PostDTO findPostById(Long id) {
        Post post = postRepository.findPostById(id);
        PostDTO postDTO = PostDTO.toDTO(post);
        return postDTO;
    }

    public List<PostDTO> findPostByUserId(Long userId) {
        List<Post> posts = postRepository.findPostByUserId(userId);
        List<PostDTO> postDTOS = posts.stream()
                .map(post -> PostDTO.toDTO(post))
                .toList();
        return postDTOS;
    }

    public List<PostDTO> findPostByCategoryId(Long categoryId) {
        List<Post> posts = postRepository.findPostByCategoryId(categoryId);
        List<PostDTO> postDTOS = posts.stream()
                .map(post -> PostDTO.toDTO(post))
                .toList();
        return postDTOS;
    }

    public List<PostDTO> findAllPosts() {
        List<Post> posts = postRepository.findAllPosts();
        List<PostDTO> postDTOS = posts.stream()
                .map(post -> PostDTO.toDTO(post))
                .toList();
        return postDTOS;
    }

    public PostDTO update(PostDTO postDTO) {
        UserDTO userDTO = userService.findUserById(postDTO.getUserId());
        User user = UserDTO.toEntity(userDTO);
        CategoryDTO categoryDTO = categoryService.findCategoryById(postDTO.getCategoryId());
        Category category = CategoryDTO.toEntity(categoryDTO);
        Post post = PostDTO.toEntity(postDTO, user, category);

        Post updatedPost = postRepository.update(post);
        PostDTO updatedPostDTO = PostDTO.toDTO(updatedPost);

        return updatedPostDTO;
    }

    public void delete(Long id) {
        postRepository.delete(id);
    }

    public List<PostDTO> findPosts(PostFilter filter) {
        List<Post> posts = postRepository.findPosts(filter);
        List<PostDTO> postDTOS = posts.stream()
                .map(post -> PostDTO.toDTO(post))
                .toList();
        List<PostDTO> mutablePostDTOS = new ArrayList<>(postDTOS);
        return sort(mutablePostDTOS, filter);
    }

    private List<PostDTO> sort(List<PostDTO> postDTOS, PostFilter filter) {
        if("oldestToNewest".equals(filter.getSort())) {
            postDTOS.sort(Comparator.comparing(PostDTO::getCreatedAt));
        } else if("newestToOldest".equals(filter.getSort())) {
            postDTOS.sort(Comparator.comparing(PostDTO::getCreatedAt).reversed());
        }
        return postDTOS;
    }
}
