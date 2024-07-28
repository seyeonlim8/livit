package lim.seyeon.safe.stay.domain.Photo;

import java.util.List;

public interface PhotoRepository {
    Photo add(Photo photo);
    Photo findPhotoById(Long id);
    List<Photo> findPhotosByPostId(Long postId);
    List<Photo> findAllPhotos();
    Photo update(Photo photo);
    void delete(Long id);
}
