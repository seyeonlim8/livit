package lim.seyeon.safe.stay.presentation.DTO;

import jakarta.validation.constraints.NotNull;
import lim.seyeon.safe.stay.domain.Photo.Photo;

public class PhotoDTO {
    private Long id;
    @NotNull
    private String url;
    private Long postId;

    public PhotoDTO() {}

    public PhotoDTO(Long id, String url, Long postId) {
        this.id = id;
        this.url = url;
        this.postId = postId;
    }

    public Long getId() {
        return id;
    }

    public @NotNull String getUrl() {
        return url;
    }

    public Long getPostId() {
        return postId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrl(@NotNull String url) {
        this.url = url;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }


}
