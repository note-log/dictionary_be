package com.snowwarrior.directory.controller;

import com.snowwarrior.directory.dto.Response;
import com.snowwarrior.directory.dto.UserDTO;
import com.snowwarrior.directory.dto.UserRegisterDTO;
import com.snowwarrior.directory.service.UserService;
import com.snowwarrior.directory.util.ResponseEntityHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public ResponseEntity<Response<String>> hello() {
        return ResponseEntityHelper.ok("success", "username", userService.hello());
    }

    @PostMapping("/register")
    public ResponseEntity<Response<String>> register(@RequestBody @Valid UserRegisterDTO userRegister) {
        try {
            userService.register(userRegister);
            return ResponseEntityHelper.ok("registration success");
        } catch (Exception e) {
            return ResponseEntityHelper.fail(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @GetMapping("/profile")
    public ResponseEntity<Response<UserDTO>> profile() {
        try {
            var result = userService.profile();
            return ResponseEntityHelper.ok("success", "profile", result);
        } catch (Exception e) {
            return ResponseEntityHelper.fail(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
        }
    }
}
