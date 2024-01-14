package com.example.bookrental.service;

import com.example.bookrental.dto.PasswordResetDto;
import jakarta.servlet.http.HttpServletRequest;

public interface PasswordResetService {
    public String requestReset(HttpServletRequest request, PasswordResetDto passwordResetDto);
}
