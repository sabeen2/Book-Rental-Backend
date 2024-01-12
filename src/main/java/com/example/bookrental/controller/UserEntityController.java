package com.example.bookrental.controller;

import com.example.bookrental.controller.basecontroller.BaseController;
import com.example.bookrental.dto.AuthenticationDto;
import com.example.bookrental.dto.UserEntityDto;
import com.example.bookrental.entity.UserEntity;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.service.PasswordResetService;
import com.example.bookrental.service.UserEntityService;
import com.example.bookrental.service.jwtservice.JwtService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "bookRental")
public class UserEntityController extends BaseController {
    private final UserEntityService userEntityService;
    private final JwtService jwtService;
    private final PasswordResetService passwordResetService;
    private final AuthenticationManager authenticationManager;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add-user")
    public GenericResponse<UserEntity> addUser(@RequestBody UserEntityDto userEntityDto) {
        return successResponse(userEntityService.addUser(userEntityDto), "New user" + userEntityDto.getUserType() + " Added");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deactivate")
    public GenericResponse<String> deactivateUser(@RequestParam Long id) {
        return successResponse(userEntityService.deactivateUser(id), "user" + id + " has been deactivated");
    }

    @PostMapping("/login")
    public GenericResponse<String> login(@RequestBody AuthenticationDto authenticationDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(), authenticationDto.getPassword()));
        if (authentication.isAuthenticated()) {
            return successResponse(jwtService.generateToken(authenticationDto.getUsername()), "Login success");
        } else {
            return errorResponse("invalid credentials");
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @PostMapping("/reset")
    public GenericResponse<String> reset(HttpServletRequest request, @RequestBody UserEntityDto userEntityDto) {
        return GenericResponse.<String>builder()
                .success(true)
                .message("password changed")
                .data(passwordResetService.requestReset(request, userEntityDto))
                .build();
    }
}