package com.example.bookrental.entity;

import jakarta.persistence.*;
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
    String email;
    String name;
    String mobileNo;
    String address;
}
