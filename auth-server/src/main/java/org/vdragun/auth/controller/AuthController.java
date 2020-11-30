package org.vdragun.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.vdragun.auth.domain.entity.Otp;
import org.vdragun.auth.domain.entity.User;
import org.vdragun.auth.domain.service.UserService;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/add")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PostMapping("/user/auth")
    public void auth(@RequestBody User user) {
        userService.authUser(user);
    }

    @PostMapping("/otp/check")
    public ResponseEntity<Void> check(@RequestBody Otp otp) {
        boolean result = userService.checkUser(otp);
        return new ResponseEntity<>(getStatusForResult(result));
    }

    private HttpStatus getStatusForResult(boolean checkResult) {
        return checkResult ? HttpStatus.OK : HttpStatus.FORBIDDEN;
    }
}
