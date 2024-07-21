package lim.seyeon.safe.stay.application;

import lim.seyeon.safe.stay.domain.User.UserRepository;
import lim.seyeon.safe.stay.domain.UserDetail.UserDetail;
import lim.seyeon.safe.stay.presentation.DTO.UserDetailDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailService {

    private UserRepository userRepository;
    private ValidationService validationService;

    public UserDetailService(UserRepository userRepository, ValidationService validationService) {
        this.userRepository = userRepository;
        this.validationService = validationService;
    }

    public UserDetailDTO add(UserDetailDTO userDetailDTO) {}

    public UserDetailDTO findUserDetailByUserId(Long userId) {

    }

    public List<UserDetailDTO> findAll() {

    }

    public UserDetailDTO update(UserDetailDTO userDetailDTO) {

    }

    public void delete(Long userId) {

    }
}
