package com.snowwarrior.directory.service;

import com.snowwarrior.directory.constant.UserRoleConstants;
import com.snowwarrior.directory.auth.JwtUser;
import com.snowwarrior.directory.dto.ProfileDTO;
import com.snowwarrior.directory.dto.UserLoginDTO;
import com.snowwarrior.directory.exception.BannedException;
import com.snowwarrior.directory.exception.NotAuditException;
import com.snowwarrior.directory.exception.NotFoundException;
import com.snowwarrior.directory.mapper.UserMapper;
import com.snowwarrior.directory.model.User;
import com.snowwarrior.directory.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthService(UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public JwtUser authLogin(UserLoginDTO userLogin) {
        String username = userLogin.getUsername();
        String password = userLogin.getPassword();

        Optional<User> userOptional = userMapper.getUserByUsername(username);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("学号不存在 " + username);
        }
        User user = userOptional.get();
        if (user.isDeleted()) throw new NotFoundException("账号不存在");
        if (this.bCryptPasswordEncoder.matches(password, user.getPassword())) {
            if (!user.isAudit()) throw new NotAuditException("该账号未审核通过，无法登陆");
            if (user.isBanned()) throw new BannedException("该账号被封禁，无法登陆");
            try {
                userMapper.onLoginUpdate(user.getUsername());
            } catch (Exception e) {
                // do nothing or print logger
                System.out.println("更新登录信息失败！");
            }
            String token = JwtUtils.generateToken(username, user.isAdmin() ?
                    UserRoleConstants.ROLE_ADMIN : UserRoleConstants.ROLE_USER);
            ProfileDTO userDTO = new ProfileDTO();
            userDTO.setUsername(username);

            return new JwtUser(token, userDTO);
        }
        throw new BadCredentialsException("用户名或密码错误");
    }
}
