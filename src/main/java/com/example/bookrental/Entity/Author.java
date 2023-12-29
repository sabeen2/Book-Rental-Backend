package com.example.bookrental.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Author")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
@Id
        @GeneratedValue(strategy = GenerationType.AUTO)
    long Author_id;
    String name;
    String email;
    String mobile_number;
}
