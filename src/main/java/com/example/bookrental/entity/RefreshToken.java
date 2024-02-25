package com.example.bookrental.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class RefreshToken {
    @Id
    @SequenceGenerator(name = "refresh_seq", initialValue = 0, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "refresh_seq")
    Long id;
    String token;
    Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_refreshToken_usersEntity"))
    UserEntity user;
}
