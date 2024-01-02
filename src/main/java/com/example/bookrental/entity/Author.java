package com.example.bookrental.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_author")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    //Sequence Generator
    @SequenceGenerator(name = "primary_key_generator", initialValue = 0, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "primary_key_generator")
    Long authorId;
    String name;
    String email;
    String mobileNumber;
}
