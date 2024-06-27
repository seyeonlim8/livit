package lim.seyeon.safe.stay.domain.User;

import lim.seyeon.safe.stay.presentation.DTO.UserDTO;

public interface UserService {
    UserDTO findUserByUsername(String username);
    UserDTO findUserById(Long id);
    UserDTO save(UserDTO userDTO);
    Boolean existsByUsername(String username);
}
