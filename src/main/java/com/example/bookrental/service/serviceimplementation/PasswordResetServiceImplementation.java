package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.ForgotPasswordDto;
import com.example.bookrental.dto.PasswordResetDto;
import com.example.bookrental.dto.ResetTokenDto;
import com.example.bookrental.entity.ResetTokenEntity;
import com.example.bookrental.entity.UserEntity;
import com.example.bookrental.exception.CustomMessageSource;
import com.example.bookrental.exception.ExceptionMessages;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.repo.ResetTokenRepo;
import com.example.bookrental.repo.UserEntityRepo;
import com.example.bookrental.securityconfig.UserInfoDetailService;
import com.example.bookrental.service.PasswordResetService;
import com.example.bookrental.service.jwtservice.JwtService;
import com.example.bookrental.utils.MailUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.config.CustomRepositoryImplementationDetector;
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
    private final CustomMessageSource messageSource;
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
                        .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
                user.setPassword(newPassword);
                userEntityRepo.save(user);
                return messageSource.get(ExceptionMessages.SUCCESS.getCode());
            } else {
                throw new NotFoundException(messageSource.get(ExceptionMessages.INVALID_CREDENTIALS.getCode()));
//                return messageSource.get(ExceptionMessages.INVALID_CREDENTIALS.getCode());
            }
        } else {
            return messageSource.get(ExceptionMessages.AUTHENTICATION_ERROR.getCode());
        }

    }

    @Transactional
    @Override
    public String forgotPassword(ForgotPasswordDto forgotPasswordDto) {
        String username = forgotPasswordDto.getUsername();
        UserEntity user = userEntityRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
        String to = user.getUsername();
        String sub = "reset token";
        String resetToken = otpGenerator();
        int resetTokenEntity = resetTokenRepo.userCount(username);

        if(resetTokenEntity!=1) {
            saveResetToken(user.getUsername(), resetToken);
        }else{
            resetTokenRepo.updateToken(user.getUsername(), resetToken);
        }

        String emailBody = MailUtils.resetTemplet(to, sub, resetToken);
        mailUtils.sendMail(to, sub, emailBody);
        return messageSource.get(ExceptionMessages.MAIL_SENT.getCode());
    }

    public String reset(ResetTokenDto resetTokenDto) {
        ResetTokenEntity resetToken = resetTokenRepo.findByUsername(resetTokenDto.getUsername())
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));

        String username = resetToken.getUsername();

        UserEntity user = userEntityRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));

        if (resetTokenDto.getToken().equals(resetToken.getToken())) {
            String password = passwordEncoder.encode(resetTokenDto.getPassword());
            user.setPassword(password);
            userEntityRepo.save(user);
        } else {
            throw new NotFoundException(messageSource.get(ExceptionMessages.INVALID_CREDENTIALS.getCode()));
        }
        resetTokenRepo.delete(resetToken);

        return messageSource.get(ExceptionMessages.SUCCESS.getCode());
    }

    void saveResetToken(String username, String token) {
        ResetTokenEntity resetTokenEntity = new ResetTokenEntity();
        resetTokenEntity.setUsername(username);
        resetTokenEntity.setToken(token);
        resetTokenRepo.save(resetTokenEntity);
    }

    public String otpGenerator() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}

