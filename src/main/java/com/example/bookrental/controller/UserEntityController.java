package com.example.bookrental.controller;

import com.example.bookrental.controller.basecontroller.BaseController;
import com.example.bookrental.dto.AuthenticationDto;
import com.example.bookrental.dto.PasswordResetDto;
import com.example.bookrental.dto.RefreshTokenReq;
import com.example.bookrental.dto.UserEntityDto;
import com.example.bookrental.dto.responsedto.AuthResponse;
import com.example.bookrental.dto.responsedto.UserResponseDto;
import com.example.bookrental.entity.RefreshToken;
import com.example.bookrental.entity.UserEntity;
import com.example.bookrental.exception.CustomMessageSource;
import com.example.bookrental.exception.ExceptionMessages;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.repo.UserEntityRepo;
import com.example.bookrental.service.PasswordResetService;
import com.example.bookrental.service.RefreshTokenService;
import com.example.bookrental.service.UserEntityService;
import com.example.bookrental.service.jwtservice.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
@Tag(name = "User controller", description = "Manages APIs for Login, Creating user,Reset password,Deactivate account")
@SecurityRequirement(name = "bookRental")
public class UserEntityController extends BaseController {
    private final UserEntityService userEntityService;
    private final JwtService jwtService;
    private final PasswordResetService passwordResetService;
    private final AuthenticationManager authenticationManager;
    private final CustomMessageSource messageSource;
    private final UserEntityRepo userEntityRepo;
    private final RefreshTokenService refreshTokenService;


    @Operation(summary = "Add Users" ,description = "Add users and provide them Roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" ,description = "User added"),
            @ApiResponse(responseCode = "500" ,description = "internal server error"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add-user")
    public GenericResponse<String> addUser(@RequestBody UserEntityDto userEntityDto) {
        return successResponse(userEntityService.addUser(userEntityDto), "New user " + userEntityDto.getUserType() + " Added");
    }
    @Operation(summary = "Deactivate Users" ,description = "Deactivate users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" ,description = "User deactivated"),
            @ApiResponse(responseCode = "404" ,description = "User Not found"),
            @ApiResponse(responseCode = "500" ,description = "internal server error"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deactivate")
    public GenericResponse<String> deactivateUser(@RequestParam Long id) {
        return successResponse(userEntityService.deactivateUser(id), "user " + id + " has been deactivated");
    }

    @Operation(summary = "Reactivate Users" ,description = "Reactivate users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" ,description = "User activated"),
            @ApiResponse(responseCode = "404" ,description = "User Not found"),
            @ApiResponse(responseCode = "500" ,description = "internal server error"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/reactivate")
    public GenericResponse<String> reActivateUser(@RequestParam Long id) {
        return successResponse(userEntityService.reactivateUser(id), "user " + id + " has been activated");
    }

    @Operation(summary = "Login" ,description = "User login ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" ,description = "User Logged in"),
            @ApiResponse(responseCode = "404" ,description = "User Not found"),
            @ApiResponse(responseCode = "500" ,description = "internal server error"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
    })
    @PostMapping("/login")
    public GenericResponse<AuthResponse> login(@RequestBody AuthenticationDto authenticationDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(), authenticationDto.getPassword()));
        if (authentication.isAuthenticated()) {
            UserEntity byUsername = userEntityRepo.findByUsername(authentication.getName())
                    .orElseThrow(()->new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
            if(byUsername.isDeleted()){
                throw new NotFoundException(messageSource.get(ExceptionMessages.INVALID_CREDENTIALS.getCode()));
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authenticationDto.getUsername());
            String role=String.valueOf(byUsername.getUserType());

            AuthResponse response=AuthResponse.builder()
                    .accessToken(jwtService.generateToken(authenticationDto.getUsername(),role))
                    .refreshToken(refreshToken.getToken())
                    .build();

//            return successResponse(jwtService.generateToken(authenticationDto.getUsername(),role), messageSource.get(ExceptionMessages.SUCCESS.getCode()));

            return successResponse(response, messageSource.get(ExceptionMessages.SUCCESS.getCode()));
        } else {
            return errorResponse(messageSource.get(ExceptionMessages.INVALID_CREDENTIALS.getCode()));
        }
    }

    @Operation(summary = "Refresh token" ,description = "Refresh token for users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" ,description = "Token generated"),
            @ApiResponse(responseCode = "404" ,description = "invalid"),
            @ApiResponse(responseCode = "500" ,description = "internal server error"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
    })
    @PostMapping("/refreshToken")
    public GenericResponse<AuthResponse> refreshToken(@RequestBody RefreshTokenReq refreshTokenReq){
        RefreshToken token = refreshTokenService.getToken(refreshTokenReq.getToken());
        refreshTokenService.verifyToken(token);
        UserEntity user=token.getUser();
        String role = String.valueOf(user.getUserType());
        String refreshedToken= jwtService.generateToken(user.getUsername(),role);
        AuthResponse response=AuthResponse.builder()
                .accessToken(refreshedToken)
                .refreshToken(refreshTokenReq.getToken())
                .build();
        return successResponse(response, messageSource.get(ExceptionMessages.SUCCESS.getCode()));
    }


    @Operation(summary = "Reset User's password" ,description = "reset users password based on authentication token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" ,description = "Password reset Success"),
            @ApiResponse(responseCode = "404" ,description = "User Not found"),
            @ApiResponse(responseCode = "500" ,description = "internal server error"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
    })
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @PostMapping("/reset")
    public GenericResponse<String> reset(HttpServletRequest request, @RequestBody PasswordResetDto passwordResetDto) {
        return GenericResponse.<String>builder()
                .success(true)
                .message("password changed")
                .data(passwordResetService.requestReset(request, passwordResetDto))
                .build();
    }

    @Operation(summary = "get users" ,description = "get users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" ,description = "All users"),
            @ApiResponse(responseCode = "404" ,description = "User Not found"),
            @ApiResponse(responseCode = "500" ,description = "internal server error"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
    })
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @GetMapping("/get-all-users")
    public GenericResponse<List<UserResponseDto>> getAll() {
        return GenericResponse.<List<UserResponseDto>>builder()
                .success(true)
                .message("password changed")
                .data(userEntityService.getUsers())
                .build();
    }

}