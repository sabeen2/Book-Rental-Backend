package com.example.bookrental.Entity;


import com.example.bookrental.Enum.RENT_TYPE;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_book_transaction")
public class BookTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    long code;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Book.class)
    Book book;

    @JsonFormat(pattern = "yyyy-mm-dd")
    Date from_date;

    @JsonFormat(pattern = "yyyy-mm-dd")
    Date to_date;

    @Column(name = "rent_status")
            @Enumerated(EnumType.STRING)
    @NonNull
     RENT_TYPE rent_type;

    @OneToOne(cascade =CascadeType.ALL)
            @JoinColumn(name = "FK_member_id")
    @NonNull
    Member member;


}
