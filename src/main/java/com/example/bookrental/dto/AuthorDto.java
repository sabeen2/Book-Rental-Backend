package com.example.bookrental.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    Long authorId;

    @NotNull(message = "Name Cannot be empty")
    String name;
    @Email(message = "Enter valid Email")
    String email;
    @NotNull(message = "Mobile-No cannot be empty")
    String mobileNumber;
}
