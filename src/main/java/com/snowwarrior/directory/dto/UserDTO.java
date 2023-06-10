package com.snowwarrior.directory.dto;

import com.snowwarrior.directory.model.User;
import org.springframework.beans.BeanUtils;

/**
 * @author SnowWarrior
 */
public class UserDTO {
    private String username;
    private String phone;
    private String email;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void fromUser(User user) {
        BeanUtils.copyProperties(user, this);
    }
}
