package com.snowwarrior.directory.service;

import com.snowwarrior.directory.dto.ClazzDTO;
import com.snowwarrior.directory.dto.MajorDTO;
import com.snowwarrior.directory.exception.AlreadyExistsException;
import com.snowwarrior.directory.dto.ProfileDTO;
import com.snowwarrior.directory.dto.UserRegisterDTO;
import com.snowwarrior.directory.exception.InvalidOperationException;
import com.snowwarrior.directory.exception.NotFoundException;
import com.snowwarrior.directory.exception.OperationFailedException;
import com.snowwarrior.directory.mapper.ClazzMapper;
import com.snowwarrior.directory.mapper.MajorMapper;
import com.snowwarrior.directory.mapper.UserMapper;
import com.snowwarrior.directory.model.Paginator;
import com.snowwarrior.directory.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author SnowWarrior
 */
@Service
public class UserService {

    private final UserMapper userMapper;
    private final MajorMapper majorMapper;
    private final ClazzMapper clazzMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserMapper userMapper, MajorMapper majorMapper,
                       ClazzMapper clazzMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userMapper = userMapper;
        this.majorMapper = majorMapper;
        this.clazzMapper = clazzMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void register(UserRegisterDTO dto) {
        Optional<User> userOptional = userMapper.getUserByUsername(dto.getUsername());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsException("学号已存在");
        }
        try {
            User user = dto.toUser();
            String encryptPassword = bCryptPasswordEncoder.encode(dto.getPassword());
            user.setPassword(encryptPassword);
            userMapper.addUser(user);
        } catch (DataIntegrityViolationException ex) {
            throw new AlreadyExistsException("学号已存在");
        } catch (Exception ex) {
            throw new OperationFailedException();
        }
    }

    public ProfileDTO profile() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOptional = userMapper.getUserByUsername(username);
        if (userOptional.isEmpty()) throw new NotFoundException("个人资料获取失败");
        if (!userOptional.get().isAudit()) throw new InvalidOperationException("用户未通过审核，无法获取资料");
        if (userOptional.get().isDeleted()) throw new InvalidOperationException("用户已被删除，无法获取资料");
        User user = userOptional.get();
        ProfileDTO dto = new ProfileDTO();
        ProfileDTO.fromUser(user);
        return dto;
    }

    public void updateProfile(ProfileDTO dto) {
        Optional<User> userOptional = userMapper.getUserByUsername(dto.getUsername());
        if (userOptional.isEmpty()) throw new NotFoundException("用户不存在，无法更新");
        if (!userOptional.get().isAudit()) throw new InvalidOperationException("用户未通过审核，无法更新");
        if (userOptional.get().isDeleted()) throw new InvalidOperationException("用户已被删除，无法更新");
        try {
            User user = dto.toUser();
            userMapper.updateUserByUsername(user);
        } catch (Exception e) {
            throw new OperationFailedException();
        }
    }

    public Paginator<ProfileDTO> getUserByName(Long id, int size, String name) {
        User[] users = userMapper.getUserByName(id, size, name);
        var profiles = Arrays.stream(users).map(ProfileDTO::fromUser).toList().toArray(new ProfileDTO[]{});
        return new Paginator<>(users[users.length - 1].getId(), profiles);
    }

    public ClazzDTO[] getClazzList() {
        var clazz = clazzMapper.getAllClazz();
        return Arrays.stream(clazz).map(ClazzDTO::fromClazz).toList().toArray(new ClazzDTO[]{});
    }

    public MajorDTO[] getMajorList() {
        var majors = majorMapper.getAllMajor();
        return Arrays.stream(majors).map(MajorDTO::fromMajor).toList().toArray(new MajorDTO[]{});
    }
}
