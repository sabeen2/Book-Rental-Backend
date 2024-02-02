package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.ForgotPasswordDto;
import com.example.bookrental.dto.PasswordResetDto;
import com.example.bookrental.dto.ResetTokenDto;
import com.example.bookrental.entity.ResetTokenEntity;
import com.example.bookrental.entity.UserEntity;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.repo.ResetTokenRepo;
import com.example.bookrental.repo.UserEntityRepo;
import com.example.bookrental.securityconfig.UserInfoDetailService;
import com.example.bookrental.service.PasswordResetService;
import com.example.bookrental.service.jwtservice.JwtService;
import com.example.bookrental.utils.MailUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImplementation implements PasswordResetService {
    private final JwtService jwtService;
    private final UserInfoDetailService userInfoDetailService;
    private final UserEntityRepo userEntityRepo;
    private final PasswordEncoder passwordEncoder;
    private final ResetTokenRepo resetTokenRepo;

    private final ObjectMapper objectMapper;


    private final MailUtils mailUtils;

    @Override
    public String requestReset(HttpServletRequest request, PasswordResetDto passwordResetDto) {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails userDetails = userInfoDetailService.loadUserByUsername(jwtService.extractUsername(token));
            String newPassword = passwordEncoder.encode(passwordResetDto.getNewPassword());
            if (passwordEncoder.matches(passwordResetDto.getOldPassword(), userDetails.getPassword())) {
                UserEntity user = userEntityRepo.findByUsername(jwtService.extractUsername(token))
                        .orElseThrow(() -> new NotFoundException("user not found"));
                user.setPassword(newPassword);
                userEntityRepo.save(user);
                return "Password changed! USERNAME";
            } else {
                return "incorrect password";
            }
        } else {
            return "user not authenticated";
        }

    }

    @Override
    public String forgotPassword(ForgotPasswordDto forgotPasswordDto) {
        String username = forgotPasswordDto.getUsername();
        UserEntity user = userEntityRepo.findByUsername(username).orElseThrow(() -> new NotFoundException("user does not exist"));
        String to = user.getUsername();
        String sub = "reset token";
        String resetToken = otpGenerator();
        saveResetToken(user.getUsername(),resetToken);
        String emailBody = MailUtils.resetTemplet(to, sub, resetToken);
        mailUtils.sendMail(to, sub, emailBody);
        return "password reset token sent";
    }

    public String reset(ResetTokenDto resetTokenDto){
        ResetTokenEntity resetToken=resetTokenRepo.findByUsername(resetTokenDto.getUsername())
                .orElseThrow(()->new NotFoundException("provided username do not have a reset token"));

        String username=resetToken.getUsername();

        UserEntity user=userEntityRepo.findByUsername(username)
                .orElseThrow(()->new NotFoundException(("specified user does not exist in our records")));

        if(resetTokenDto.getUsername().equals(resetToken.getUsername())&&resetTokenDto.getToken().equals(resetToken.getToken())){
            String password=passwordEncoder.encode(resetTokenDto.getPassword());
            user.setPassword(password);
            userEntityRepo.save(user);
        }else{
            throw new NotFoundException("invalid token or username");
        }
        resetTokenRepo.delete(resetToken);

        return "password reset successful";
    }

    void saveResetToken(String username,String token){
        ResetTokenEntity resetTokenEntity=new ResetTokenEntity();
        resetTokenEntity.setUsername(username);
        resetTokenEntity.setToken(token);
        resetTokenRepo.save(resetTokenEntity);
    }

    public String otpGenerator() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}

