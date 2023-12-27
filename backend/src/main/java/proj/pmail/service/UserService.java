package proj.pmail.service;

import proj.pmail.dto.UserDTO;
import proj.pmail.entity.User;

public interface UserService {

    User getUserByUsername(String username);

    boolean addUser(User user);

    boolean updatePassword(UserDTO userDTO) throws Exception;

    boolean updateNickname(UserDTO userDTO);

    boolean deleteUserByUsername(String username) throws Exception;
}
