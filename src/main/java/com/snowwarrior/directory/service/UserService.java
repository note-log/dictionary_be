package com.snowwarrior.directory.service;

import com.snowwarrior.directory.exception.AlreadyExistsException;
import com.snowwarrior.directory.dto.UserDTO;
import com.snowwarrior.directory.dto.UserRegisterDTO;
import com.snowwarrior.directory.mapper.UserMapper;
import com.snowwarrior.directory.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author SnowWarrior
 */
@Service
public class UserService {

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void register(UserRegisterDTO dto) {
        Optional<User> userOptional = this.getUserByName(dto.getUsername());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsException("Save failed,the username already exist.");
        }
        User user = User.convertOfUserRegisterDTO(dto);
        String cryptPassword = bCryptPasswordEncoder.encode(dto.getPassword());
        user.setPassword(cryptPassword);
        try {
            userMapper.addUser(user);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("Save failed,the username already exist.");
        }
    }

    public Optional<User> getUserByName(String username) {
        return userMapper.getUserByUsername(username);
    }

    public UserDTO profile() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOptional = this.getUserByName(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("can't get your profile.");
        }
        User user = userOptional.get();
        UserDTO dto = new UserDTO();
        dto.fromUser(user);
        return dto;
    }


    public String hello() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
