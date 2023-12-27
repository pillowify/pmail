package proj.pmail.dto;

import proj.pmail.entity.User;
import lombok.Data;

@Data
public class UserDTO {

    private User user;
    private Boolean stayLoggedIn;
    private String newPassword;
}
