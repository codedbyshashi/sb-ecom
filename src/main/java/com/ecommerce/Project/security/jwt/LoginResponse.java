package com.ecommerce.Project.security.jwt;

import java.util.List;

public class LoginResponse {
    private String jwtToken;

    private String username;
    private List<String> roles;

    public LoginResponse(String jwrToken, String username, List<String> roles) {
        this.jwtToken = jwrToken;
        this.username = username;
        this.roles = roles;
    }

    public String getJwrToken() {
        return jwtToken;
    }

    public void setJwrToken(String jwrToken) {
        this.jwtToken = jwrToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
