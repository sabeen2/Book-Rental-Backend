package com.example.bookrental.service;

import com.example.bookrental.dto.ForgotPasswordDto;
import com.example.bookrental.dto.PasswordResetDto;
import com.example.bookrental.dto.ResetTokenDto;
import jakarta.servlet.http.HttpServletRequest;

public interface PasswordResetService {
     String requestReset(HttpServletRequest request, PasswordResetDto passwordResetDto);
     String forgotPassword( ForgotPasswordDto forgotPasswordDto);

     String reset(ResetTokenDto resetTokenDto);

}
