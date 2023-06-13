package com.snowwarrior.directory.controller;

import com.snowwarrior.directory.auth.JwtUser;
import com.snowwarrior.directory.dto.Response;
import com.snowwarrior.directory.dto.UserLoginDTO;
import com.snowwarrior.directory.service.AuthService;
import com.snowwarrior.directory.util.ResponseEntityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Response<String>> login(@RequestBody UserLoginDTO userLogin) {
        JwtUser jwtUser = authService.authLogin(userLogin);
        return ResponseEntityHelper.ok("登陆成功", "token", jwtUser.getToken());
    }
}
