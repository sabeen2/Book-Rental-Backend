package com.example.bookrental.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Member")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Member {
    @Id
            @GeneratedValue(strategy = GenerationType.AUTO)
    long memberid;
    String Email;
    String Name;
    String mobileNo;
    String address;
}
