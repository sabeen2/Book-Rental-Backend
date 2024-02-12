package com.example.bookrental.dto.responsedto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    Long id;
    String username;
    String userType;
    boolean deleted;
}
