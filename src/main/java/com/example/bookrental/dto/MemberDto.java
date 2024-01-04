package com.example.bookrental.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MemberDto {
    Long memberid;
    @Email(message = "Email cannot be Empty")
    String email;
    @NotNull(message = "Name Cannot be empty")
    String name;

    @NotNull(message = "Mobile-No Cannot be empty")
    String mobileNo;
    @NotNull(message = "Address Cannot be empty")
    String address;
}
