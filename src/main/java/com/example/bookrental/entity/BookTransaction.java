package com.example.bookrental.entity;


import com.example.bookrental.auditingconfig.AuditingEntity;
import com.example.bookrental.enums.RentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_book_transaction",uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
//@SQLDelete(sql = "UPDATE tbl_book_transaction SET deleted = true WHERE id=?")
//@Where(clause ="deleted=true")
@SQLDelete(sql = "UPDATE tbl_book_transaction SET deleted = true, rent_status = 'RETURN' WHERE id=?")
@Where(clause = "deleted = false AND rent_status = 'RENT'")



public class BookTransaction  extends AuditingEntity {
    @Id
    @SequenceGenerator(name = "transaction_primary_key_generator", initialValue = 0, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "transaction_primary_key_generator")
    Long id;

    @Column(unique=true)
    Long code;

//    @NotNull(message = "Book Cannot be empty")
    @ManyToOne(targetEntity = Book.class ,cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    Book book;

    @JsonFormat(pattern = "yyyy-mm-dd")
    Date fromDate;

    @JsonFormat(pattern = "yyyy-mm-dd")
    Date toDate;

    @Column(name = "rent_status")
    @Enumerated(EnumType.STRING)
    RentType rentType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},targetEntity = Member.class)
    @JoinColumn(name = "memberid")

    Member member;
    private boolean deleted = Boolean.FALSE;

    String username;
}
