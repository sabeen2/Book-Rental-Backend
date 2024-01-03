package com.example.bookrental.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    Long authorId;

    @NotNull(message = "Cannot be empty")
    String name;
    String email;
    String mobileNumber;
}
