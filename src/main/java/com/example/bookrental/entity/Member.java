package com.example.bookrental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "tbl_member")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Member {
    @Id
    @SequenceGenerator(name = "primary_key_generator", initialValue = 0, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "primary_key_generator")
    Long memberid;
    @Email( message = "Email cannot be Empty")
    String email;
    @NotNull(message = "Cannot be empty")
    String name;
    @NotNull(message = "Cannot be empty")
    String mobileNo;
    String address;
}
