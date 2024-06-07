package lim.seyeon.safe.stay.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User save(User user);
}
