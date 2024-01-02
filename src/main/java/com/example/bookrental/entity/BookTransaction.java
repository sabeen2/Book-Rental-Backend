package com.example.bookrental.entity;


import com.example.bookrental.enums.RENT_TYPE;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_book_transaction")
public class BookTransaction {
    @Id
    @SequenceGenerator(name = "primary_key_generator", initialValue = 0, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "primary_key_generator")
    Long id;

    Long code;

    @ManyToOne(targetEntity = Book.class)
    Book book;

    @JsonFormat(pattern = "yyyy-mm-dd")
    Date fromDate;

    @JsonFormat(pattern = "yyyy-mm-dd")
    Date toDate;

    @Column(name = "rent_status")
    @Enumerated(EnumType.STRING)
    RENT_TYPE rentType;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "memberid")
    Member member;


}
