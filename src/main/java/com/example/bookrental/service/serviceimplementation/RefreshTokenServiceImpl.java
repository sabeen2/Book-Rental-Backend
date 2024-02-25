package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.entity.RefreshToken;
import com.example.bookrental.entity.UserEntity;
import com.example.bookrental.exception.CustomMessageSource;
import com.example.bookrental.exception.ExceptionMessages;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.repo.RefreshTokenRepo;
import com.example.bookrental.repo.UserEntityRepo;
import com.example.bookrental.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepo refreshTokenRepo;
    private final UserEntityRepo userEntityRepo;
    private final CustomMessageSource messageSource;


    @Override
    public RefreshToken createRefreshToken(String username) {
        UserEntity user = userEntityRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));

        Optional<RefreshToken> existingRefreshToken = refreshTokenRepo.findByUser(user);

        if (existingRefreshToken.isPresent()) {
            RefreshToken refreshToken = existingRefreshToken.get();
            refreshToken.setToken(String.valueOf(UUID.randomUUID()));
            refreshToken.setExpiryDate(Instant.now().plusMillis(600000));
            return refreshTokenRepo.save(refreshToken);
        } else {
            RefreshToken token = RefreshToken.builder()
                    .user(userEntityRepo.findByUsername(username)
                            .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode()))))
                    .token(String.valueOf(UUID.randomUUID()))
                    .expiryDate(Instant.now().plusMillis(600000))
                    .build();
            return refreshTokenRepo.save(token);
        }
    }


    @Override
    public RefreshToken verifyToken(RefreshToken token) {
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepo.delete(token);
           throw new NotFoundException(messageSource.get(ExceptionMessages.EXPIRED.getCode()));
        }
        return token;
    }

    @Override
    public RefreshToken getToken(String token) {
     return refreshTokenRepo.findByToken(token)
                .orElseThrow(()->new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND_EXCEPTION.getCode())));
    }
}
