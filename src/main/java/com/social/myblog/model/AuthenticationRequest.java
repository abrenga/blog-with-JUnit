package com.social.myblog.model;

import lombok.Getter;
import lombok.Setter;

public class AuthenticationRequest {
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }


}
