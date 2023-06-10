package com.snowwarrior.directory.service;

import com.snowwarrior.directory.constant.UserRoleConstants;
import com.snowwarrior.directory.auth.JwtUser;
import com.snowwarrior.directory.dto.UserDTO;
import com.snowwarrior.directory.dto.UserLoginDTO;
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

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthService(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public JwtUser authLogin(UserLoginDTO userLogin) {
        String username = userLogin.getUsername();
        String password = userLogin.getPassword();

        Optional<User> userOptional = userService.getUserByName(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        User user = userOptional.get();
        if (this.bCryptPasswordEncoder.matches(password, user.getPassword())) {
            String token = JwtUtils.generateToken(username, UserRoleConstants.ROLE_USER);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);

            return new JwtUser(token, userDTO);
        }
        throw new BadCredentialsException("The username or password error.");
    }
}
