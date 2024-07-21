package lim.seyeon.safe.stay.application;

import lim.seyeon.safe.stay.domain.UserDetail.UserDetail;
import lim.seyeon.safe.stay.domain.UserDetail.UserDetailRepository;
import lim.seyeon.safe.stay.presentation.DTO.UserDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailService {

    private UserDetailRepository userDetailRepository;
    private ValidationService validationService;

    @Autowired
    public UserDetailService(UserDetailRepository userDetailRepository, ValidationService validationService) {
        this.userDetailRepository = userDetailRepository;
        this.validationService = validationService;
    }

    public UserDetailDTO add(UserDetailDTO userDetailDTO) {
        UserDetail userDetail = UserDetailDTO.toEntity(userDetailDTO);
        validationService.checkValid(userDetail);

        UserDetail savedUserDetail = userDetailRepository.add(userDetail);
        UserDetailDTO savedUserDetailDTO = UserDetailDTO.toDTO(savedUserDetail);
        return savedUserDetailDTO;
    }

    public UserDetailDTO findUserDetailByUserId(Long userId) {
        UserDetail userDetail = userDetailRepository.findUserDetailByUserId(userId);
        UserDetailDTO userDetailDTO = UserDetailDTO.toDTO(userDetail);
        return userDetailDTO;
    }

    public List<UserDetailDTO> findAll() {
        List<UserDetail> userDetails = userDetailRepository.findAll();
        List<UserDetailDTO> userDetailDTOS = userDetails.stream()
                .map(userDetail -> UserDetailDTO.toDTO(userDetail))
                .toList();
        return userDetailDTOS;
    }

    public UserDetailDTO update(UserDetailDTO userDetailDTO) {
        UserDetail userDetail = UserDetailDTO.toEntity(userDetailDTO);
        UserDetail updatedUserDetail = userDetailRepository.update(userDetail);
        UserDetailDTO updatedUserDetailDTO = UserDetailDTO.toDTO(updatedUserDetail);
        return updatedUserDetailDTO;
    }

    public void delete(Long userId) {
        userDetailRepository.delete(userId);
    }
}
