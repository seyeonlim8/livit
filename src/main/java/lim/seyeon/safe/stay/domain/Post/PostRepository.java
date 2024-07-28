package lim.seyeon.safe.stay.domain.Post;

import java.util.List;

public interface PostRepository {
    Post add(Post post);
    Post findPostById(Long id);
    List<Post> findPostByUserId(Long userId);
    List<Post> findPostByCategoryId(Long categoryId);
    List<Post> findAllPosts();
    Post update(Post post);
    void delete(Long id);
}
