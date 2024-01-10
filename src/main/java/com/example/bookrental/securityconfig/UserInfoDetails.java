package com.example.bookrental.securityconfig;

import com.example.bookrental.entity.UserEntity;
import com.example.bookrental.enums.USER_TYPE;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoDetails implements UserDetails {

    private final String username;
    private final String password;

    private final List<GrantedAuthority> authorities;


    public UserInfoDetails(UserEntity userEntity) {
        username = userEntity.getUsername();
        password = userEntity.getPassword();
        authorities = Arrays.stream(USER_TYPE.values())
                .filter(userType -> userEntity.getUserType() == userType)
                .map(userType -> new SimpleGrantedAuthority("ROLE_" + userType.name()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return true;
    }
}
