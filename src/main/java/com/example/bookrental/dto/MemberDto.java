package com.example.bookrental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MemberDto {
    Long memberid;
    String email;
    String name;
    String mobileNo;
    String address;
}
