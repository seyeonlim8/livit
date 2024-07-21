package lim.seyeon.safe.stay.domain.UserDetail;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailRepository {
    UserDetail add(UserDetail userDetail);
    UserDetail findUserDetailByUserId(Long userId);
    List<UserDetail> findAll();
    UserDetail update(UserDetail userDetail);
    void delete(Long userId);
}
