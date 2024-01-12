package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.UserEntityDto;
import com.example.bookrental.entity.UserEntity;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.repo.UserEntityRepo;
import com.example.bookrental.securityconfig.UserInfoDetailService;
import com.example.bookrental.service.PasswordResetService;
import com.example.bookrental.service.jwtservice.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImplementation implements PasswordResetService {
private final JwtService jwtService;
private final UserInfoDetailService userInfoDetailService;
private final UserEntityRepo userEntityRepo;
private final PasswordEncoder passwordEncoder;
    @Override
    public String requestReset(HttpServletRequest request, UserEntityDto userEntityDto) {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails userDetails = userInfoDetailService.loadUserByUsername(jwtService.extractUsername(token));
            if (userDetails.getUsername().equals(userEntityDto.getUsername())) {
                updatePassword(userDetails, userEntityDto.getPassword());
            } else {
                throw new NotFoundException("User mismatch");
            }
        } else {
            throw new NotFoundException("Authentication Failed");
        }
        return "Password changed! USERNAME=" + userEntityDto.getUsername();
    }
    public void updatePassword(UserDetails userDetails, String newPassword) {
        UserEntity userEntity = userEntityRepo.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String encodedPassword = passwordEncoder.encode(newPassword);
        userEntity.setPassword(encodedPassword);
        userEntityRepo.save(userEntity);
    }
}

