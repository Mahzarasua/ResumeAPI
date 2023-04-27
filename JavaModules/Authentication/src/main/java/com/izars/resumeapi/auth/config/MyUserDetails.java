package com.izars.resumeapi.auth.config;

import com.izars.resumeapi.auth.model.Role;
import com.izars.resumeapi.auth.model.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {
    private String username;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorityList;

    public MyUserDetails(User userModel) {
        this.username = userModel.getUsername();
        //PasswordEncoder encoder = SpringUtils.getBean(BCryptPasswordEncoder.class);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(userModel.getPassword());
        this.active = userModel.isActive();
        this.authorityList = Arrays.stream(
                        getRoles(userModel).split(","))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @NotNull
    private String getRoles(User userModel) {
        StringBuilder builder = new StringBuilder();
        for (Role role : userModel.getRoles()) {
            builder.append(role.getRole()).append(",");
        }
        return builder.toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
