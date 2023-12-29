package com.example.bookrental.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Book")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String name;
    double rating;
    int stock;
    Date published_date;
    String photo;


    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Catagory.class)
    private Catagory catagory;

    @ManyToMany
    @JoinTable(name = "book_author", // Specify the name of the intermediate table
            joinColumns = @JoinColumn(name = "book_id"), // Column in the book table
            inverseJoinColumns = @JoinColumn(name = "author_id") // Column in the author table
    )
    List<Author> authors;
}
