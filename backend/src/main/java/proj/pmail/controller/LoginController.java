package proj.pmail.controller;

import proj.pmail.dto.UserDTO;
import proj.pmail.service.LoginService;
import proj.pmail.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public R login(@RequestBody UserDTO userDTO) {
        String token = loginService.login(userDTO);
        if ("".equals(token)) {
            return new R(false, "username does not exist or password is wrong", null);
        } else if ("-1".equals(token)) {
            return new R(false, "failed to login", null);
        } else {
            return new R(true, null, token);
        }
    }
}
