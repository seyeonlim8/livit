package lim.seyeon.safe.stay.presentation;

import jakarta.validation.constraints.NotNull;
import lim.seyeon.safe.stay.domain.User;

public class UserDTO {
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    public UserDTO() {}

    public UserDTO(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO [username=" + username + ", password=" + password + "]";
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getPassword()
        );
        return user;
    }

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword()
        );
        return userDTO;
    }
}
