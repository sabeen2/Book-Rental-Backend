package com.example.bookrental.dto.responsedto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    String accessToken;
    String refreshToken;
}
