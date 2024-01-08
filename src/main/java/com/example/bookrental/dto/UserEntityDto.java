package com.example.bookrental.dto;

import com.example.bookrental.enums.USER_TYPE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntityDto {
    String username;
    String password;
    @Enumerated(EnumType.STRING)
    USER_TYPE userType;
}
