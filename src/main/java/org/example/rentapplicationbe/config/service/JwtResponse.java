package org.example.rentapplicationbe.config.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {
    private Long id;
    private String token;
    private String type = "Bearer";
    private String username;
    private String name;
    private String avatarUrl;

    public JwtResponse(Long id, String token, String type, String username, String name, String avatarUrl, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.token = token;
        this.type = type;
        this.username = username;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.authorities = authorities;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getType() {
        return type;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }


    private final Collection<? extends GrantedAuthority> authorities;

    public JwtResponse(Long id, String token, String username, String name, Collection<? extends GrantedAuthority> authorities, String avatarUrl) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.name = name;
        this.authorities = authorities;
        this.avatarUrl = avatarUrl;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
