package lim.seyeon.safe.stay.application;

import lim.seyeon.safe.stay.domain.EntityNotFoundException;
import lim.seyeon.safe.stay.domain.User;
import lim.seyeon.safe.stay.domain.UserRepository;
import lim.seyeon.safe.stay.domain.UserService;
import lim.seyeon.safe.stay.presentation.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    // BCryptPasswordEncoder: PasswordEncoder 인터페이스를 구현함.
    //                        BCrypt 해싱 알고리즘을 사용해 비밀번호를 해싱함.
    @Autowired
    PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        UserDTO userDTO = UserDTO.toDTO(user);
        return userDTO;
    }

    @Override
    public UserDTO findUserById(Long id) {
        User user = userRepository.findUserById(id);
        if(user == null) {
            throw new EntityNotFoundException("User with id " + id + " not found.");
        }
        return UserDTO.toDTO(user);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = new User(
                userDTO.getId(),
                userDTO.getUsername(),
                passwordEncoder.encode(userDTO.getPassword())
        );
        User savedUser = userRepository.save(user);
        UserDTO savedUserDTO = UserDTO.toDTO(savedUser);
        return savedUserDTO;
    }
}
