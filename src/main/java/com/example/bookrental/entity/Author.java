package com.example.bookrental.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Cannot be empty")
    String name;

    @Email(message = "Enter valid Email")
    String email;
    String mobileNumber;
}
