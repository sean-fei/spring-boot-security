package com.sean.auth.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 安全用户模型
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/8 20:49
 */
@Data
public class JwtUserDetails implements UserDetails {

    private static final long serialVersionUID = 5455947879940116590L;

    private long userId;
    private String username;
    private String password;
    private String salt;
    private Integer state;
    private Collection<? extends GrantedAuthority> authorities;

    JwtUserDetails() {

    }

    public JwtUserDetails(String username, String password, String salt, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}
