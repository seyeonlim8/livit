package lim.seyeon.safe.stay.domain;

import lim.seyeon.safe.stay.presentation.UserDTO;

public interface UserService {
    UserDTO findUserByUsername(String username);
    UserDTO findUserById(Long id);
    UserDTO save(UserDTO userDTO);
}
