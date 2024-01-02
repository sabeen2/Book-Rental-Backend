package com.example.bookrental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    Long authorId;
    String name;
    String email;
    String mobileNumber;
}
