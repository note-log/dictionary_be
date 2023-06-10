package com.snowwarrior.directory.controller;

import com.snowwarrior.directory.auth.JwtUser;
import com.snowwarrior.directory.dto.Response;
import com.snowwarrior.directory.dto.UserLoginDTO;
import com.snowwarrior.directory.service.AuthService;
import com.snowwarrior.directory.util.ResponseEntityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
        try {
            JwtUser jwtUser = authService.authLogin(userLogin);

            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.set("Authorization", "Bearer " + jwtUser.getToken());
            return ResponseEntityHelper.ok("login success", "token", jwtUser.getToken(), httpHeaders);
        } catch (BadCredentialsException e) {
            return ResponseEntityHelper.fail(HttpStatus.UNAUTHORIZED);
        }
    }
}
