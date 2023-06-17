package com.snowwarrior.directory.controller;

import com.snowwarrior.directory.dto.*;
import com.snowwarrior.directory.model.Paginator;
import com.snowwarrior.directory.service.UserService;
import com.snowwarrior.directory.util.ResponseEntityHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/register")
    public ResponseEntity<Response<String>> register(@RequestBody @Valid UserRegisterDTO userRegister) {
        userService.register(userRegister);
        return ResponseEntityHelper.ok("注册成功");
    }

    @GetMapping("/profile")
    public ResponseEntity<Response<ProfileDTO>> profile() {
        var result = userService.profile();
        return ResponseEntityHelper.ok("success", "profile", result);
    }

    @GetMapping("/profile/search")
    public ResponseEntity<Response<Paginator<ProfileDTO>>> getProfile(@RequestParam Optional<String> page,
                                                                      @RequestParam Optional<String> size,
                                                                      @RequestParam String name) {
        long rPage = 1L;
        int rSize = 20;
        if (page.isPresent()) {
            try {
                rPage = Long.parseLong(page.get());
            } catch (NumberFormatException ex) {
                // Do nothing
            }
        }
        if (size.isPresent()) {
            try {
                rSize = Integer.parseInt(size.get());
            } catch (NumberFormatException ex) {
                // do nothing
            }
        }
        var result = userService.getUserByName(rPage, rSize, name);
        return ResponseEntityHelper.ok("success", "data", result);
    }

    @GetMapping("/profile/list")
    public ResponseEntity<Response<Paginator<ProfileDTO>>> getProfile(@RequestParam Optional<String> page,
                                                                      @RequestParam Optional<String> size) {
        long rPage = 1L;
        int rSize = 20;
        if (page.isPresent()) {
            try {
                rPage = Long.parseLong(page.get());
            } catch (NumberFormatException ex) {
                // Do nothing
            }
        }
        if (size.isPresent()) {
            try {
                rSize = Integer.parseInt(size.get());
            } catch (NumberFormatException ex) {
                // do nothing
            }
        }
        var result = userService.getUserList(rPage, rSize);
        return ResponseEntityHelper.ok("success", "data", result);
    }

    @PostMapping("/password")
    public ResponseEntity<Response<String>> changePassword(@RequestBody PasswordChangeDTO dto) {
        userService.changePassword(dto.oldPassword, dto.newPassword);
        return ResponseEntityHelper.ok("success");
    }

    @PostMapping("/profile")
    public ResponseEntity<Response<String>> updateProfile(@RequestBody @Valid ProfileDTO profileDTO) {
        userService.updateProfile(profileDTO);
        return ResponseEntityHelper.ok("更新成功");
    }

    @GetMapping("/major/list")
    public ResponseEntity<Response<MajorDTO[]>> getMajorList() {
        var result = userService.getMajorList();
        return ResponseEntityHelper.ok("success", "list", result);
    }

    @GetMapping("/clazz/list")
    public ResponseEntity<Response<ClazzDTO[]>> getClazzList() {
        var result = userService.getClazzList();
        return ResponseEntityHelper.ok("success", "list", result);
    }
}
