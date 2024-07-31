package lim.seyeon.safe.stay.infrastructure;

import lim.seyeon.safe.stay.application.LikeService;
import lim.seyeon.safe.stay.presentation.DTO.LikeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

// Custom Thymeleaf utility class to check if the current user liked the post
@Component
public class LikeUtil {

    private LikeService likeService;

    @Autowired
    public LikeUtil(LikeService likeService) {
        this.likeService = likeService;
    }

    public boolean isLiked(Long postId, Long userId) {
        try {
            Optional<LikeDTO> like = Optional.ofNullable(likeService.findLikeByPostIdAndUserId(postId, userId));
            return like.isPresent();
        } catch (Exception e) {
            return false;  // If any exception occurs, consider it as not liked
        }
    }

    public Optional<LikeDTO> getLike(Long postId, Long userId) {
        try {
            return Optional.ofNullable(likeService.findLikeByPostIdAndUserId(postId, userId));
        } catch (Exception e) {
            return Optional.empty();  // Return empty if any exception occurs
        }
    }

    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }
}

