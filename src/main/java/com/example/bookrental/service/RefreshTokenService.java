package com.example.bookrental.service;

import com.example.bookrental.entity.RefreshToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface RefreshTokenService {
     RefreshToken createRefreshToken(String username);
     RefreshToken verifyToken(RefreshToken token);
     RefreshToken getToken(String token);

//     void updateRefreshToken(String token,String username);


}
