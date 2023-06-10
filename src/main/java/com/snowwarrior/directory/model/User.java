package com.snowwarrior.directory.model;

import com.snowwarrior.directory.dto.UserRegisterDTO;
import org.springframework.beans.BeanUtils;

/**
 * user模型
 *
 * @author SnowWarrior
 */
public class User {
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String email;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static User convertOfUserRegisterDTO(UserRegisterDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);

        return user;
    }
}
