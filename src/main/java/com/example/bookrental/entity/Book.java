package com.example.bookrental.entity;

import com.example.bookrental.auditingconfig.AuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_book",uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@Getter
@Setter

@SQLDelete(sql = "UPDATE tbl_book SET deleted = true WHERE id = ?")
@Where(clause ="deleted=false")
public class Book extends AuditingEntity {
    @Id
    @SequenceGenerator(name = "book_primary_key_generator", initialValue = 0, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "book_primary_key_generator")
    Long id;

    String name;

    Double rating;

    Integer stock;

    Date publishedDate;

    String photo;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Category.class)
    private Category category;

    @ManyToMany
    @JoinTable(name = "book_author", // Specify the name of the intermediate table
            joinColumns = @JoinColumn(name = "book_id"), // Column in the book table
            inverseJoinColumns = @JoinColumn(name = "author_id") // Column in the author table
    )
    List<Author> authors;

    private boolean deleted = Boolean.FALSE;
}