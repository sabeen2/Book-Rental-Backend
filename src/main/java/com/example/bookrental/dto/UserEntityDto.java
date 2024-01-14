package com.example.bookrental.dto;

import com.example.bookrental.enums.UserType;
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
    UserType userType;
}
