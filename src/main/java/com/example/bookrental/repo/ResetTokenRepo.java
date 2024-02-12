package com.example.bookrental.repo;

import com.example.bookrental.entity.ResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ResetTokenRepo extends JpaRepository<ResetTokenEntity,Long> {
   Optional <ResetTokenEntity> findByUsername(String email);
}
