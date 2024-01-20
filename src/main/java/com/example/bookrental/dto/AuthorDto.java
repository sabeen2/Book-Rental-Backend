package com.example.bookrental.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    Long authorId;

    @NotNull(message = "Name Cannot be empty")
    String name;

    @Email(message = "Enter valid Email")
    String email;

    @NotNull(message = "Mobile-No cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$",message = "invalid phone")
    String mobileNumber;
}
