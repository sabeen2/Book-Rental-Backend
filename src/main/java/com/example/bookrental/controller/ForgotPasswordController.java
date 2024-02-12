package com.example.bookrental.controller;

import com.example.bookrental.controller.basecontroller.BaseController;
import com.example.bookrental.dto.ForgotPasswordDto;
import com.example.bookrental.dto.ResetTokenDto;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.service.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reset-password")
@Tag(name = "Forgot password Controller", description = "APIs for Forgot password")
@RequiredArgsConstructor
public class ForgotPasswordController extends BaseController {

    private final PasswordResetService passwordResetService;


    @Operation(summary = "Forgot password generate Otp", description = "Generate Otp for password reset")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Otp sent"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PostMapping("/generate-Otp")
    public GenericResponse<String> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto){
        return successResponse(passwordResetService.forgotPassword(forgotPasswordDto),"password reset token sent");
    }
    @Operation(summary = "Forgot password generate Otp", description = "Generate Otp for password reset")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reset success"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PostMapping("/reset")
    public GenericResponse<String> resetPassword(@RequestBody ResetTokenDto  resetTokenDto){
        return successResponse(passwordResetService.reset(resetTokenDto),"password reset successfully");
    }
}

