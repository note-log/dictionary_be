package com.snowwarrior.directory.service;

import com.snowwarrior.directory.dto.ClazzDTO;
import com.snowwarrior.directory.dto.MajorDTO;
import com.snowwarrior.directory.exception.AlreadyExistsException;
import com.snowwarrior.directory.exception.InvalidOperationException;
import com.snowwarrior.directory.exception.NotFoundException;
import com.snowwarrior.directory.exception.OperationFailedException;
import com.snowwarrior.directory.mapper.ClazzMapper;
import com.snowwarrior.directory.mapper.MajorMapper;
import com.snowwarrior.directory.mapper.UserMapper;
import com.snowwarrior.directory.model.Clazz;
import com.snowwarrior.directory.model.Paginator;
import com.snowwarrior.directory.model.Major;
import com.snowwarrior.directory.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AdminService {
    private final UserMapper userMapper;
    private final MajorMapper majorMapper;
    private final ClazzMapper clazzMapper;

    @Autowired
    public AdminService(UserMapper userMapper, MajorMapper majorMapper, ClazzMapper clazzMapper) {
        this.userMapper = userMapper;
        this.majorMapper = majorMapper;
        this.clazzMapper = clazzMapper;
    }

    public void approve(String username) {
        var userOptional = userMapper.getUserByUsername(username);
        if (userOptional.isEmpty()) throw new NotFoundException("用户不存在");
        var user = userOptional.get();
        if (user.isDeleted()) throw new NotFoundException("用户不存在");
        if (user.isAudit()) throw new InvalidOperationException("用户已审核通过");
        try {
            userMapper.approve(username);
        } catch (Exception ex) {
            throw new OperationFailedException();
        }
    }

    // 获取待审核用户
    public Paginator<User> getAuditUsers(Long page, int size) {
        var users = userMapper.getAuditUsers((page - 1) * size, size);
        Long count = userMapper.getAuditTotalCount();
        return new Paginator<>(count, users);
    }

    public Paginator<Major> getMajorList(Long page, int size) {
        var majors = majorMapper.getMajorList((page - 1) * size, size);
        Long count = majorMapper.getTotalCount();
        return new Paginator<>(count, majors);
    }

    public void addMajor(MajorDTO dto) {
        Optional<Major> majorOptional = majorMapper.getMajorByName(dto.getName());
        if (majorOptional.isPresent()) throw new AlreadyExistsException("专业已存在，无法添加");
        try {
            var major = dto.toMajor();
            majorMapper.addMajor(major);
        } catch (Exception ex) {
            throw new OperationFailedException();
        }
    }

    public void updateMajor(MajorDTO dto) {
        Optional<Major> majorOptional = majorMapper.getMajorById(dto.getId());
        if (majorOptional.isEmpty()) throw new NotFoundException("未找到专业，无法更新");
        try {
            var major = dto.toMajor();
            majorMapper.updateMajor(major);
        } catch (Exception ex) {
            throw new OperationFailedException();
        }
    }

    public void deleteMajor(Long id) {
        Optional<Major> major = majorMapper.getMajorById(id);
        if (major.isEmpty()) throw new InvalidOperationException("专业不存在，无法删除");
        if (major.get().isDeleted()) throw new InvalidOperationException("无法操作，专业已被删除");
        try {
            majorMapper.deleteMajor(id);
        } catch (Exception ex) {
            throw new OperationFailedException();
        }
    }

    public Paginator<Clazz> getClazzList(Long page, int size) {
        var clazz = clazzMapper.getClazzList((page - 1) * size, size);
        Long count = clazzMapper.getTotalCount();
        return new Paginator<>(count, clazz);
    }

    public void addClazz(ClazzDTO dto) {
        Optional<Clazz> clazzOptional = clazzMapper.getClazzByName(dto.getName());
        if (clazzOptional.isPresent()) throw new AlreadyExistsException("班级已存在，无法添加");
        try {
            var clazz = dto.toClazz();
            clazzMapper.addClazz(clazz);
        } catch (Exception ex) {
            throw new OperationFailedException();
        }
    }

    public void updateClazz(ClazzDTO dto) {
        Optional<Clazz> clazzOptional = clazzMapper.getClazzById(dto.getId());
        if (clazzOptional.isEmpty()) throw new NotFoundException("班级不存在，无法更新班级信息");
        try {
            var clazz = dto.toClazz();
            clazzMapper.updateClazz(clazz);
        } catch (Exception ex) {
            throw new OperationFailedException();
        }
    }

    public void deleteClazz(Long id) {
        Optional<Clazz> clazzOptional = clazzMapper.getClazzById(id);
        if (clazzOptional.isEmpty()) throw new NotFoundException("班级不存在，无法删除");
        var clazz = clazzOptional.get();
        if (clazz.isDeleted()) throw new InvalidOperationException("班级已经被删除，无法再次删除");
        try {
            clazzMapper.deleteClazz(id);
        } catch (Exception ex) {
            throw new OperationFailedException();
        }
    }

    public void ban(String username) {
        String myUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (myUsername.equals(username)) throw new InvalidOperationException("无法封禁自己");
        var userOptional = userMapper.getUserByUsername(username);
        if (userOptional.isEmpty()) throw new NotFoundException("用户不存在");
        var user = userOptional.get();
        if (user.isDeleted()) throw new NotFoundException("无效操作，用户已删除");
        if (user.isBanned()) throw new InvalidOperationException("无效操作，用户已被封禁");
        if (!user.isAudit()) throw new InvalidOperationException("无效操作，用户还未通过审核");
        try {
            userMapper.ban(username);
        } catch (Exception ex) {
            throw new OperationFailedException();
        }
    }

    public void unban(String username) {
        String myUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (myUsername.equals(username)) throw new InvalidOperationException("无法解封自己");
        var userOptional = userMapper.getUserByUsername(username);
        if (userOptional.isEmpty()) throw new NotFoundException("用户不存在");
        var user = userOptional.get();
        if (user.isDeleted()) throw new NotFoundException("无效操作，用户已删除");
        if (!user.isBanned()) throw new InvalidOperationException("无效操作，用户未被封禁");
        if (!user.isAudit()) throw new InvalidOperationException("无效操作，用户还未通过审核");
        try {
            userMapper.unban(username);
        } catch (Exception ex) {
            throw new OperationFailedException();
        }
    }

    public Paginator<User> getBannedUserList(Long page, int size) {
        var users = userMapper.getBannedUser((page - 1) * size, size);
        Long count = userMapper.getBannedCount();
        return new Paginator<>(count, users);
    }

    public User getProfile(String username) {
        var userOptional = userMapper.getUserByUsername(username);
        if (userOptional.isEmpty()) throw new NotFoundException("用户不存在");
        return userOptional.get();
    }

    public Paginator<User> getProfileList(Long page, int size) {
        var users = userMapper.getUsers((page - 1) * size, size);
        Long count = userMapper.getRealTotalCount();
        return new Paginator<>(count, users);
    }

    public void delete(String username) {
        String myUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (myUsername.equals(username)) throw new InvalidOperationException("无法删除自己");
        var userOptional = userMapper.getUserByUsername(username);
        if (userOptional.isEmpty()) throw new NotFoundException("用户不存在");
        var user = userOptional.get();
        if (user.isAudit()) throw new InvalidOperationException("无效操作，用户已审核通过，无法删除");
        try {
            userMapper.deleteUserByUsername(username);
        } catch (Exception ex) {
            throw new OperationFailedException();
        }
    }
}
