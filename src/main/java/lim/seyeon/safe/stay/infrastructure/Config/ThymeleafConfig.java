package lim.seyeon.safe.stay.infrastructure.Config;

import lim.seyeon.safe.stay.application.LikeService;
import lim.seyeon.safe.stay.infrastructure.LikeUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymeleafConfig {

    private LikeService likeService;

    public ThymeleafConfig(LikeService likeService) {
        this.likeService = likeService;
    }

    @Bean
    public LikeUtil likeUtil() {
        return new LikeUtil(likeService);
    }
}
