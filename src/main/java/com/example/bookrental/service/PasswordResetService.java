package com.example.bookrental.service;

import com.example.bookrental.dto.UserEntityDto;
import jakarta.servlet.http.HttpServletRequest;

public interface PasswordResetService {
    public String requestReset(HttpServletRequest request,UserEntityDto userEntityDto);
}
