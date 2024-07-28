package lim.seyeon.safe.stay.domain.Like;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository {
    Like add(Like like);
    Like findLikeById(Long id);
    List<Like> findLikesByUserId(Long userId);
    List<Like> findLikesByPostId(Long postId);
    List<Like> findAllLikes();
    Like update(Like like);
    void delete(Long id);
}
