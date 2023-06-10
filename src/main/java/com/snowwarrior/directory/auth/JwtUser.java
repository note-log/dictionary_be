package com.snowwarrior.directory.auth;

import com.snowwarrior.directory.dto.UserDTO;

/**
 * @author SnowWarrior
 */
public class JwtUser {
    private UserDTO user;
    private String token;

    public JwtUser(String token, UserDTO user) {
        this.user = user;
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
