package lim.seyeon.safe.stay.presentation.DTO;

import jakarta.validation.constraints.NotNull;
import lim.seyeon.safe.stay.domain.UserDetail.UserDetail;

public class UserDetailDTO {

    @NotNull
    private Long userId;

    private String fullName;

    private String email;

    public UserDetailDTO() {}

    public UserDetailDTO(Long userId, String fullName, String email) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static UserDetail toEntity(UserDetailDTO userDetailDTO) {
        UserDetail userDetail = new UserDetail(
                userDetailDTO.getUserId(),
                userDetailDTO.getFullName(),
                userDetailDTO.getEmail()
        );
        return userDetail;
    }

    public static UserDetailDTO toDTO(UserDetail userDetail) {
        UserDetailDTO userDetailDTO = new UserDetailDTO(
                userDetail.getUserId(),
                userDetail.getFullName(),
                userDetail.getEmail()
        );
        return userDetailDTO;
    }
}
