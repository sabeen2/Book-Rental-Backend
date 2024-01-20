package com.example.bookrental.dto;

import com.example.bookrental.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntityDto {

    @Email(message = "please enter a valid email")
    String username;
    String password;
    @Enumerated(EnumType.STRING)
    UserType userType;
}
