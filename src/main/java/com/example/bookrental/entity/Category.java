package com.example.bookrental.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_catagory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @SequenceGenerator(name = "primary_key_generator", initialValue = 0, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "primary_key_generator")
    Long id;
    String name;
    String discription;
}
