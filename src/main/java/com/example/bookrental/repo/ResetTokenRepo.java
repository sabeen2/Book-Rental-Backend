package com.example.bookrental.repo;

import com.example.bookrental.entity.ResetTokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ResetTokenRepo extends JpaRepository<ResetTokenEntity, Long> {
    Optional<ResetTokenEntity> findByUsername(String email);

    @Modifying
    @Query(value = "update reset_token_entity set token=?2 where username= ?1", nativeQuery = true)
    void updateToken(String username,String token);

    @Query(value = "select count(username) from reset_token_entity rte where username =?1",nativeQuery = true)
    int userCount(String username);


}
