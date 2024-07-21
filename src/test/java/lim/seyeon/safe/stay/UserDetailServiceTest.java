package lim.seyeon.safe.stay;

import lim.seyeon.safe.stay.application.UserDetailService;
import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.presentation.DTO.UserDetailDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class UserDetailServiceTest {

    @Autowired
    private UserDetailService userDetailService;

    @Test
    @DisplayName("Retrieves the newly added user detail when user id is queried")
    void addAndFindUserDetailByUserIdTest() {
        UserDetailDTO userDetailDTO = new UserDetailDTO(1L, "John Doe", "example@livit.com");

        UserDetailDTO savedUserDetailDTO = userDetailService.add(userDetailDTO);
        UserDetailDTO foundUserDetailDTO = userDetailService.findUserDetailByUserId(1L);

        assertEquals(savedUserDetailDTO.getUserId(), foundUserDetailDTO.getUserId());
        assertEquals(savedUserDetailDTO.getFullName(), foundUserDetailDTO.getFullName());
        assertEquals(savedUserDetailDTO.getEmail(), foundUserDetailDTO.getEmail());
    }

    @Test
    @DisplayName("Throws EntityNotFoundException when a non-existent user id is queried")
    void findUserDetailByUserIdTest() {
        Long notFoundUserId = -1L;
        assertThrows(EntityNotFoundException.class, () -> userDetailService.findUserDetailByUserId(notFoundUserId));
    }

    @Test
    @DisplayName("All user details should be retrieved")
    void findAllUserDetailsTest() {
        UserDetailDTO userDetailDTO1 = new UserDetailDTO(1L, "John Doe1", "example1@livit.com");
        UserDetailDTO userDetailDTO2 = new UserDetailDTO(2L, "John Doe2", "example2@livit.com");

        userDetailService.add(userDetailDTO1);
        userDetailService.add(userDetailDTO2);

        assertEquals(2, userDetailService.findAll().size());
    }

    @Test
    @DisplayName("User detail should be successfully updated")
    void updateUserDetailTest() {
        UserDetailDTO userDetailDTO = new UserDetailDTO(1L, "John Doe", "example@livit.com");
        userDetailService.add(userDetailDTO);

        userDetailDTO.setFullName("Seyeon Lim");
        userDetailService.update(userDetailDTO);
        UserDetailDTO foundUserDetailDTO = userDetailService.findUserDetailByUserId(1L);

        assertEquals(userDetailDTO.getUserId(), foundUserDetailDTO.getUserId());
        assertEquals("Seyeon Lim", foundUserDetailDTO.getFullName());
        assertEquals(userDetailDTO.getEmail(), foundUserDetailDTO.getEmail());
    }

    @Test
    @DisplayName("User detail should be successfully deleted")
    void deleteUserDetailTest() {
        UserDetailDTO userDetailDTO = new UserDetailDTO(1L, "John Doe", "example@livit.com");
        userDetailService.add(userDetailDTO);

        userDetailService.delete(userDetailDTO.getUserId());
        assertThrows(EntityNotFoundException.class, () -> userDetailService.findUserDetailByUserId(1L));
    }
}
