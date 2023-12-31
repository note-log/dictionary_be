package com.snowwarrior.directory.controller;

import com.snowwarrior.directory.dto.*;
import com.snowwarrior.directory.exception.InvalidOperationException;
import com.snowwarrior.directory.model.Clazz;
import com.snowwarrior.directory.model.Major;
import com.snowwarrior.directory.model.Paginator;
import com.snowwarrior.directory.model.User;
import com.snowwarrior.directory.service.AdminService;
import com.snowwarrior.directory.util.ResponseEntityHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/approval")
    public ResponseEntity<Response<String>> approve(@RequestBody ApprovalDTO dto) {
        adminService.approve(dto.username);
        return ResponseEntityHelper.ok("success");
    }

    @GetMapping("/audit/list")
    public ResponseEntity<Response<Paginator<User>>> getAuditUser(@RequestParam Optional<String> page,
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
        var paginate = adminService.getAuditUsers(rPage, rSize);
        return ResponseEntityHelper.ok("success", "list", paginate);
    }

    @PostMapping("/ban")
    public ResponseEntity<Response<String>> ban(@RequestBody @Valid BanDTO dto) {
        adminService.ban(dto.username);
        return ResponseEntityHelper.ok("success");
    }

    @PostMapping("/unban")
    public ResponseEntity<Response<String>> unban(@RequestBody @Valid BanDTO dto) {
        adminService.unban(dto.username);
        return ResponseEntityHelper.ok("success");
    }

    @GetMapping("/profile")
    public ResponseEntity<Response<User>> getProfile(@RequestParam String username) {
        if (username == null || username.isEmpty())
            throw new InvalidOperationException("参数错误，学号为空，无法获取用户信息");
        var user = adminService.getProfile(username);
        return ResponseEntityHelper.ok("success", "data", user);
    }

    @GetMapping("/profile/list")
    public ResponseEntity<Response<Paginator<User>>> getProfileList(@RequestParam Optional<String> page,
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
        var paginate = adminService.getProfileList(rPage, rSize);
        return ResponseEntityHelper.ok("success", "list", paginate);
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<Response<String>> delete(@RequestParam String username) {
        if (username == null || username.isEmpty())
            throw new InvalidOperationException("参数错误，学号为空，无法删除用户");
        adminService.delete(username);
        return ResponseEntityHelper.ok("success");
    }

    @GetMapping("/major/list")
    public ResponseEntity<Response<Paginator<Major>>> getMajorList(@RequestParam Optional<String> page,
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
        var result = adminService.getMajorList(rPage, rSize);
        return ResponseEntityHelper.ok("success", "list", result);
    }

    @GetMapping("/clazz/list")
    public ResponseEntity<Response<Paginator<Clazz>>> getClazzList(@RequestParam Optional<String> page,
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
        var result = adminService.getClazzList(rPage, rSize);
        return ResponseEntityHelper.ok("success", "list", result);
    }

    @GetMapping("/banned")
    public ResponseEntity<Response<Paginator<User>>> getBannedList(@RequestParam Optional<String> page,
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
        var result = adminService.getBannedUserList(rPage, rSize);
        return ResponseEntityHelper.ok("success", "list", result);
    }

    @PutMapping("/major")
    public ResponseEntity<Response<String>> addMajor(@RequestBody MajorDTO dto) {
        if (dto.getName() == null || dto.getName().isEmpty())
            throw new InvalidOperationException("专业名为空，无法新建班级");
        adminService.addMajor(dto);
        return ResponseEntityHelper.ok("success");
    }

    @PutMapping("/clazz")
    public ResponseEntity<Response<String>> addClazz(@RequestBody ClazzDTO dto) {
        if (dto.getName() == null || dto.getName().isEmpty())
            throw new InvalidOperationException("参数错误，班级名为空，无法新建班级");
        adminService.addClazz(dto);
        return ResponseEntityHelper.ok("success");
    }

    @PostMapping("/major")
    public ResponseEntity<Response<String>> updateMajor(@RequestBody @Valid MajorDTO dto) {
        adminService.updateMajor(dto);
        return ResponseEntityHelper.ok("success");
    }

    @PostMapping("/clazz")
    public ResponseEntity<Response<String>> updateClazz(@RequestBody @Valid ClazzDTO dto) {
        adminService.updateClazz(dto);
        return ResponseEntityHelper.ok("success");
    }

    @DeleteMapping("/major")
    public ResponseEntity<Response<String>> deleteMajor(@RequestParam Long id) {
        if (id == null || id <= 0)
            throw new InvalidOperationException("参数错误，无法删除专业");
        adminService.deleteMajor(id);
        return ResponseEntityHelper.ok("success");
    }

    @DeleteMapping("/clazz")
    public ResponseEntity<Response<String>> deleteClazz(@RequestParam Long id) {
        if (id == null || id <= 0)
            throw new InvalidOperationException("参数错误，无法删除班级");
        adminService.deleteClazz(id);
        return ResponseEntityHelper.ok("success");
    }
}
