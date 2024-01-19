package com.example.bookrental.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "tbl_author")
@SQLDelete(sql = "UPDATE tbl_author SET deleted = true WHERE author_id = ?")
@Where(clause ="deleted=false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Author {
    @Id
    //Sequence Generator
    @SequenceGenerator(name = "author_primary_key_generator", initialValue = 0, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "author_primary_key_generator")
    Long authorId;

    @Column(unique=true)
    String name;

    @Column(unique=true)
    String email;

    @Column(unique=true)
    String mobileNumber;
    private boolean deleted = Boolean.FALSE;
}
