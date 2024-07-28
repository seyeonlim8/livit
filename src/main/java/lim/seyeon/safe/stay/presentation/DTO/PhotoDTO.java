package lim.seyeon.safe.stay.presentation.DTO;

import jakarta.validation.constraints.NotNull;
import lim.seyeon.safe.stay.domain.Photo.Photo;
import lim.seyeon.safe.stay.domain.Post.Post;

public class PhotoDTO {
    private Long id;
    @NotNull
    private String url;
    private Long postId;

    public PhotoDTO() {}

    public PhotoDTO(String url, Long postId) {
        this.url = url;
        this.postId = postId;
    }

    public PhotoDTO(Long id, String url, Long postId) {
        this.id = id;
        this.url = url;
        this.postId = postId;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Long getPostId() {
        return postId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public static Photo toEntity(PhotoDTO photoDTO, Post post) {
        Photo photo = new Photo(
                photoDTO.getId(),
                photoDTO.getUrl(),
                post
        );
        return photo;
    }

    public static PhotoDTO toDTO(Photo photo) {
        PhotoDTO photoDTO = new PhotoDTO(
                photo.getId(),
                photo.getUrl(),
                photo.getPost().getId()
        );
        return photoDTO;
    }
}
