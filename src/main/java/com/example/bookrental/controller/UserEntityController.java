package com.example.bookrental.controller;

import com.example.bookrental.dto.AuthenticationDto;
import com.example.bookrental.dto.UserEntityDto;
import com.example.bookrental.entity.UserEntity;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.service.UserEntityService;
import com.example.bookrental.service.jwtservice.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserEntityController {
    private final UserEntityService userEntityService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add-user")
    public GenericResponse<UserEntity> addUser(@RequestBody UserEntityDto userEntityDto) {
        return GenericResponse.<UserEntity>builder()
                .success(true)
                .message("New User Added")
                .data(userEntityService.addUser(userEntityDto))
                .build();
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deactivate")
    public GenericResponse<String> deactivateUser(@RequestParam Long id) {
        return GenericResponse.<String>builder()
                .success(true)
                .message("User Deactivated")
                .data(userEntityService.deactivateUser(id))
                .build();
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthenticationDto authenticationDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(), authenticationDto.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authenticationDto.getUsername());
        } else {
            throw new NotFoundException("invalid user request");
//        return jwtService.generateToken(authenticationDto.getUsername());
        }
    }
}