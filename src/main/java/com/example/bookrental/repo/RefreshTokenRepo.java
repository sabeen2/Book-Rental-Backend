package com.example.bookrental.repo;

import com.example.bookrental.entity.RefreshToken;
import com.example.bookrental.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Long> {
   Optional<RefreshToken> findByToken(String token);
   Optional<RefreshToken> findByUser(UserEntity user);
}
