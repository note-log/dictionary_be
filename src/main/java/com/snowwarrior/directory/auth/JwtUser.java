package com.snowwarrior.directory.auth;

import com.snowwarrior.directory.dto.ProfileDTO;

/**
 * @author SnowWarrior
 */
public class JwtUser {
    private ProfileDTO user;
    private String token;

    public JwtUser(String token, ProfileDTO user) {
        this.user = user;
        this.token = token;
    }

    public ProfileDTO getUser() {
        return user;
    }

    public void setUser(ProfileDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
