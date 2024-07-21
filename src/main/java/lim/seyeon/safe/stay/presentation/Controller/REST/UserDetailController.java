package lim.seyeon.safe.stay.presentation.Controller.REST;

import lim.seyeon.safe.stay.application.UserDetailService;
import lim.seyeon.safe.stay.presentation.DTO.UserDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-detail")
public class UserDetailController {

    private UserDetailService userDetailService;

    @Autowired
    public UserDetailController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @PostMapping
    public UserDetailDTO createUserDetail(@RequestBody UserDetailDTO userDetailDTO) {
        return userDetailService.add(userDetailDTO);
    }

    @GetMapping("/{userId}")
    public UserDetailDTO findUserDetailByUserId(@PathVariable Long userId) {
        return userDetailService.findUserDetailByUserId(userId);
    }

    @GetMapping
    public List<UserDetailDTO> findAllUserDetails() {
        return userDetailService.findAll();
    }

    @PutMapping
    public UserDetailDTO updateUserDetail(@RequestBody UserDetailDTO userDetailDTO) {
        return userDetailService.update(userDetailDTO);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserDetail(@PathVariable Long userId) {
        userDetailService.delete(userId);
    }
}
